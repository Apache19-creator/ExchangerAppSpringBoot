package com.herotech.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("default")
public class DevEmailService implements EmailService {

    @Override
    public void sendRegistrationEmail(EmailDetail emailDetail) {
        log.info("email verification  token => {}", emailDetail.getToken());

    }

    @Override
    public void sendPasswordResetEmail(EmailDetail emailDetail) {
        log.info("password reset  token => {}", emailDetail.getToken());

    }

    @Override
    public void sendEmailVerificationSuccessMessage(EmailDetail emailDetail) {
        log.info("email verification  token => {}", emailDetail.getToken());

    }

    @Override
    public void resendEmailVerificationOTP(EmailDetail emailDetail) {
        log.info("email verification  token => {}", emailDetail.getToken());

    }

    @Override
    public void sendAdminInviteEmail(String email) {

    }
}
