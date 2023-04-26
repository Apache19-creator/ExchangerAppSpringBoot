package com.herotech.app.controller;

import com.herotech.data.dtos.requests.ResetPasswordRequest;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.shared.services.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exchange/v1/auth")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) throws HeroXchangeException, HeroXchangeException {
        return ResponseEntity.ok(passwordService.forgotPassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) throws HeroXchangeException {
        return ResponseEntity.ok(passwordService.resetPassword(request));
    }

    @PostMapping("/resend-forgot-password-email/{email}")
    public ResponseEntity<String> resendForgotPasswordEmail(@PathVariable String email) throws  HeroXchangeException {
        return ResponseEntity.ok(passwordService.resendForgotPasswordEmail(email));
    }
}
