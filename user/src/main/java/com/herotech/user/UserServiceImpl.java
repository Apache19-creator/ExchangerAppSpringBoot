package com.herotech.user;

import com.herotech.data.dtos.requests.RegistrationRequest;
import com.herotech.data.dtos.responses.RegistrationResponse;
import com.herotech.data.dtos.responses.UserResponse;
import com.herotech.data.entities.AppUser;
import com.herotech.data.entities.VerificationToken;
import com.herotech.data.enums.Role;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.repositories.UserRepository;
import com.herotech.data.utils.Validator;
import com.herotech.notification.EmailDetail;
import com.herotech.notification.EmailService;
import com.herotech.shared.services.verificationtoken.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) throws HeroXchangeException {
        validateRegistrationRequest(registrationRequest);
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        registrationRequest.setPin(passwordEncoder.encode(registrationRequest.getPin()));
        AppUser appUser = modelMapper.map(registrationRequest, AppUser.class);
        appUser.setRole(Role.USER);
        VerificationToken verificationToken = verificationTokenService.generateEMailVerificationToken(appUser.getEmail());
        appUser.setEmailVerificationToken(verificationToken.getToken());
        userRepository.save(appUser);
        emailService.sendRegistrationEmail(new EmailDetail(appUser.getEmail(), appUser.getFirstName(), appUser.getEmailVerificationToken()));
        return modelMapper.map(appUser, RegistrationResponse.class);
    }

    private void validateRegistrationRequest(RegistrationRequest registrationRequest) throws HeroXchangeException {
        Validator.ensureValidEmail(registrationRequest.getEmail());
//        Validator.ensureValidPhone(registrationRequest.getPhoneNumber());
        Validator.ensureValidPassword(registrationRequest.getPassword());
        Validator.ensureBothPasswordMatches(registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        ensureValidPin(registrationRequest);
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new HeroXchangeException("email already exists");
        }
    }

    private static void ensureValidPin(RegistrationRequest registrationRequest) throws HeroXchangeException {
        if (registrationRequest.getPin().length() != 4) {
            throw new HeroXchangeException("pin must be four digits");
        }
        if (!registrationRequest.getPin().matches("[^a-zA-Z]+")) {
            throw new HeroXchangeException("pin must not contain alphabets");
        }
    }

    @Override
    public UserResponse getUser(Long userId) throws HeroXchangeException {
        AppUser user = userRepository.findById(userId).orElseThrow(
                () -> new HeroXchangeException("user not found"));
        return modelMapper.map(user, UserResponse.class);
    }
}
