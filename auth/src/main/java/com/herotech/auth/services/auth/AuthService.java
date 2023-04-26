package com.herotech.auth.services.auth;


import com.herotech.data.dtos.requests.LoginRequest;
import com.herotech.data.dtos.responses.LoginResponse;
import com.herotech.data.exceptions.HeroXchangeException;

public interface AuthService {
     LoginResponse login(LoginRequest loginRequest) throws HeroXchangeException;

    String logout(String email);
}
