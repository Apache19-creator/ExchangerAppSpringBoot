package com.herotech.data.appuserservice;


import com.herotech.data.dtos.responses.UserResponse;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.AppUser;

public interface AppUserService {

    AppUser findUserByEmail(String email) throws HeroXchangeException;

    void saveUser(AppUser user);

    AppUser findUserByPasswordResetToken(String passwordResetToken) throws HeroXchangeException;

    UserResponse getUser(Long userId) throws HeroXchangeException;

    AppUser findUserByEmailVerificationToken(String token) throws HeroXchangeException;
}
