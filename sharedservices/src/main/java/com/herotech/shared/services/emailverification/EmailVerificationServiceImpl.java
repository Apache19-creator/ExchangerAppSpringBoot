package com.herotech.shared.services.emailverification;

import com.herotech.data.appuserservice.AppUserService;
import com.herotech.data.entities.AppUser;
import com.herotech.data.entities.VerificationToken;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.notification.EmailDetail;
import com.herotech.notification.EmailService;
import com.herotech.shared.services.verificationtoken.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final AppUserService userService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    @Override
    public String verifyEmail(String otp) throws HeroXchangeException {
        verificationTokenService.verifyAndDeleteToken(otp);
        AppUser user = userService.findUserByEmailVerificationToken(otp);
        user.setVerified(true);
        user.setEmailVerificationToken("");
        userService.saveUser(user);
        emailService.sendEmailVerificationSuccessMessage(new EmailDetail(user.getEmail(),
                user.getFirstName(),user.getEmailVerificationToken()));
        return "Email verified Successfully";
    }

    @Override
    public String resendEmailVerificationOTP(String email) throws HeroXchangeException {
        AppUser user = userService.findUserByEmail(email);
        VerificationToken verificationToken =
                verificationTokenService.generateEMailVerificationToken(email);
        user.setEmailVerificationToken(verificationToken.getToken());
        userService.saveUser(user);
        emailService.resendEmailVerificationOTP(new EmailDetail(user.getEmail(),
                user.getFirstName(),user.getEmailVerificationToken()));
        return "email re-sent successfully";
    }
}
