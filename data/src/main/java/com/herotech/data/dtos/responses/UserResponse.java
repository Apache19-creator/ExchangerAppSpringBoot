package com.herotech.data.dtos.responses;

import com.herotech.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String profileImageUrl;
    //    private Gender gender;
//    private LocalDate dateOfBirth;
//    private Language languagePreference;
    private boolean isVerified;
    private Role role;
    private boolean enabled;
}
