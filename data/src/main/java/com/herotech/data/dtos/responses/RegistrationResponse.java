package com.herotech.data.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationResponse {
    private String firstName;
    private String lastName;
    private String email;

}