package com.herotech.shared.services.password;


import com.herotech.data.dtos.requests.ResetPasswordRequest;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.dtos.requests.AuthenticationRequest;

public interface PasswordService {
    String forgotPassword(String email) throws HeroXchangeException;

    String resetPassword(ResetPasswordRequest request) throws HeroXchangeException;

    String changePassword(AuthenticationRequest request) throws HeroXchangeException;


    String resendForgotPasswordEmail(String email) throws HeroXchangeException;

}
