package com.herotech.data.dtos.responses;

import com.herotech.data.entities.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class LoginResponse {
    private String jwtToken;
    private Long userId;
    private final String message = "login Successful";
    private KycStatus kycStatus;


    public static LoginResponse of(String jwtToken, Long userId, KycStatus status) {
        return new LoginResponse(jwtToken, userId,status);
    }
}
