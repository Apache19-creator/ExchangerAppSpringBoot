package com.herotech.shared.services.password;

import com.herotech.data.dtos.requests.ResetPasswordRequest;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.AppUser;
import com.herotech.data.dtos.requests.AuthenticationRequest;
import com.herotech.data.entities.VerificationToken;
import com.herotech.notification.EmailDetail;
import com.herotech.notification.EmailService;
import com.herotech.data.utils.Validator;

import com.herotech.data.appuserservice.AppUserService;
import com.herotech.shared.services.verificationtoken.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Override
    public String forgotPassword(String email) throws HeroXchangeException {
        AppUser user = appUserService.findUserByEmail(email);
        VerificationToken verificationToken = verificationTokenService.createPasswordResetToken(user.getEmail());
        user.setPasswordResetToken(verificationToken.getToken());
        appUserService.saveUser(user);
        emailService.sendPasswordResetEmail(new EmailDetail(user.getEmail(),
                user.getFirstName(), user.getPasswordResetToken()));
        return "password reset email sent";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) throws HeroXchangeException {
        ensureValidPassword(request);
        verificationTokenService.verifyAndDeleteToken(request.getPasswordResetToken());
        AppUser user = appUserService.findUserByPasswordResetToken(request.getPasswordResetToken());
        user.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        user.setPasswordResetToken("");
        appUserService.saveUser(user);
        return "password reset successful. continue to login";
    }

    private void ensureValidPassword(ResetPasswordRequest request) throws HeroXchangeException {
        Validator.ensureValidPassword(request.getPassword());
        Validator.ensureBothPasswordMatches(request.getPassword(), request.getConfirmPassword());
    }

    @Override
    public String changePassword(AuthenticationRequest request) throws HeroXchangeException {
        AppUser user = appUserService.findUserByEmail(request.getEmail());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getPassword().equals(user.getPassword())) throw new HeroXchangeException("invalid new password");
        user.setPassword((request.getPassword()));
        appUserService.saveUser(user);
        return "password changed";
    }

    @Override
    public String resendForgotPasswordEmail(String email) throws HeroXchangeException {
        AppUser user = appUserService.findUserByEmail(email);
        VerificationToken verificationToken =
                verificationTokenService.createPasswordResetToken(email);
        user.setPasswordResetToken(verificationToken.getToken());
        appUserService.saveUser(user);
        emailService.sendPasswordResetEmail(new EmailDetail(user.getEmail(),
                user.getFirstName(), user.getPasswordResetToken()));
        return "email re-sent successfully";
    }
}
