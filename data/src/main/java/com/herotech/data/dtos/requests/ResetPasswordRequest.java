package com.herotech.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResetPasswordRequest {
        private String passwordResetToken;
    private String password;
    private String confirmPassword;
}
