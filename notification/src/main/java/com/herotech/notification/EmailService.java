package com.herotech.notification;

public interface EmailService {
     void sendRegistrationEmail(EmailDetail emailDetail);
    void sendPasswordResetEmail(EmailDetail emailDetail);

    void sendEmailVerificationSuccessMessage(EmailDetail emailDetail);

    void resendEmailVerificationOTP(EmailDetail emailDetail);

    void sendAdminInviteEmail(String email);
}
