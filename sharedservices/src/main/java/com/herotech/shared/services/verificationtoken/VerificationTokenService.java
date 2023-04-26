package com.herotech.shared.services.verificationtoken;


import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.VerificationToken;

public interface VerificationTokenService {
     VerificationToken findVerificationTokenObject(String token) throws HeroXchangeException;

    VerificationToken createPasswordResetToken(String email);

    void verifyAndDeleteToken(String token) throws HeroXchangeException;

    void deleteToken(Long tokenId);

    VerificationToken generateEMailVerificationToken(String email);
}
