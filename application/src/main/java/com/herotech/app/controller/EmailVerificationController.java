package com.herotech.app.controller;


import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.shared.services.emailverification.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exchange/v1/auth")
@RequiredArgsConstructor
public class EmailVerificationController {
      private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify-email/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable String otp) throws HeroXchangeException {
        return ResponseEntity.ok(emailVerificationService.verifyEmail(otp));
    }

    @PostMapping("/resend-email-otp-verification-email/{email}")
    public ResponseEntity<String> resendEmailVerificationEmail(@PathVariable String email) throws HeroXchangeException {
        return ResponseEntity.ok(emailVerificationService.resendEmailVerificationOTP(email));
    }

}
