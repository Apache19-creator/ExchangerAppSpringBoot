package com.herotech.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("!default")
public class ProdEmailService implements EmailService {
    @Value("${heroXchange.app.frontend-host}")
    private String frontEndHost;


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendRegistrationEmail(EmailDetail emailDetail) {
        sendEmail(SendEmailRequest.builder()
                .emailAddress(emailDetail.getEmail())
                .subject("Welcome to Foreign Exchange App - Verify Your Email Address")
                .message(String.format("""
                        Dear %s,
                                                      
                        Welcome to Foreign Exchange App! Thank you for creating an account with us.
                        To complete your registration, please verify your email address by entering the OTP code below:
                                                      
                        OTP code: %s
                                                      
                        Please note that this code will expire in %d minutes.
                        If you have any questions or concerns, please feel free to contact our support team at %s.
                                                      
                        Thank you for choosing Foreign Exchange App.
                                                
                        Best regards,
                        The Foreign Exchange App Team
                                          """, emailDetail.getFirstName(), emailDetail.getToken(), 5, sender))
                .build());
    }

    @Override
    public void sendPasswordResetEmail(EmailDetail emailDetail) {
        sendEmail(SendEmailRequest.builder()
                .emailAddress(emailDetail.getEmail())
                .subject("Foreign Exchange App - Password Reset Request")
                .message(String.format("""
                        Dear %s,
                                                       
                        We received a request to reset your Foreign Exchange App password.
                        Please click on the link below to change your password:
                                                        
                        %s                               
                        If you did not request to reset your password, please contact our support team at [Insert contact information].
                                                        
                        Thank you for choosing Foreign Exchange App.
                                                        
                        Best regards,
                                                        
                        The Foreign Exchange App Team
                        """, emailDetail.getFirstName(), frontEndHost + "reset_password/" + emailDetail.getToken()))
                .build());
    }

    @Override
    public void sendEmailVerificationSuccessMessage(EmailDetail emailDetail) {
        sendEmail(SendEmailRequest.builder()
                .emailAddress(emailDetail.getEmail())
                .subject("Welcome to Foreign Exchange App - Account Successfully Created")
                .message(String.format("""
                        Dear %s,
                                                
                        Congratulations! Your Foreign Exchange App account has been successfully created.
                        We're excited to have you as part of our community.
                                                
                        Now you can start using the app and explore all the features we offer.
                                                
                        If you have any questions or concerns, please feel free to contact our support team at %s.
                                                
                        Thank you for choosing Foreign Exchange App.
                                                
                        Best regards,
                                                
                        The Foreign Exchange App Team
                        """, emailDetail.getFirstName(), sender))
                .build());
    }

    @Override
    public void resendEmailVerificationOTP(EmailDetail emailDetail) {
        sendEmail(SendEmailRequest.builder()
                .emailAddress(emailDetail.getEmail())
                .subject("Welcome to Foreign Exchange App - Verify Your Email Address")
                .message(String.format("""
                        Dear %s,
                                                      
                        Welcome to Foreign Exchange App! Thank you for creating an account with us.
                        To complete your registration, please verify your email address by entering the OTP code below:
                                                      
                        OTP code: %s
                                                      
                        Please note that this code will expire in %d minutes.
                        If you have any questions or concerns, please feel free to contact our support team at [Insert contact information].
                                                      
                        Thank you for choosing Foreign Exchange App.
                                                
                        Best regards,
                        The Foreign Exchange App Team
                                          """, emailDetail.getFirstName(), emailDetail.getToken(), 5))
                .build());
    }

    @Override
    public void sendAdminInviteEmail(String email) {
        sendEmail(SendEmailRequest.builder()
                .emailAddress(email)
                .subject("Welcome Xchanger Admin - Complete Account Setup")
                .message(String.format("""
                        Welcome to Foreign Exchange App! Thank you for creating an account with us.
                                              
                        If you have any questions or concerns, please feel free to contact our support team at %s.
                        Use this  <a href="%s">link</a> to finish setting up your account
                                                      
                        Thank you for choosing Foreign Exchange App.
                                                
                        Best regards,
                        The Foreign Exchange App Team
                                          """, sender, frontEndHost + "complete_admin_profile"))
                .build());
    }

    @Async
    public void sendEmail(SendEmailRequest sendEmailRequest) {
        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(sendEmailRequest.getEmailAddress());
            mailMessage.setText(sendEmailRequest.getMessage());
            mailMessage.setSubject(sendEmailRequest.getSubject());
            javaMailSender.send(mailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Error while Sending Mail");
        }
    }
}
