package com.herotech.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String pin;
    private String phoneNumber;

    public RegistrationRequest(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }


    private String password;
    private String confirmPassword;


}
