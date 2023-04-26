package com.herotech.user;

import com.herotech.data.dtos.requests.RegistrationRequest;
import com.herotech.data.dtos.responses.RegistrationResponse;
import com.herotech.data.dtos.responses.UserResponse;
import com.herotech.data.exceptions.HeroXchangeException;

public interface UserService {
    RegistrationResponse register(RegistrationRequest registrationRequest) throws HeroXchangeException;

    UserResponse getUser(Long userId) throws HeroXchangeException;
}
