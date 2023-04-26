package com.herotech.shared.services.emailverification;

import com.herotech.data.exceptions.HeroXchangeException;

public interface EmailVerificationService {
    String verifyEmail(String otp) throws HeroXchangeException;

    String resendEmailVerificationOTP(String email) throws HeroXchangeException;
}
