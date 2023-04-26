package com.herotech.shared.services.verificationtoken;


import com.herotech.data.repositories.VerificationTokenRepository;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken findVerificationTokenObject(String token) throws HeroXchangeException {
        return verificationTokenRepository.findVerificationTokenByToken(token).orElseThrow(
                () -> new HeroXchangeException("invalid request")
        );
    }

    @Override
    public VerificationToken createPasswordResetToken(String email) {
        return generateToken(email);
    }

    @Override
    public void verifyAndDeleteToken(String token) throws HeroXchangeException {
        VerificationToken verificationToken = findVerificationTokenObject(token);

        if (verificationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new HeroXchangeException("This token has expired!!");
        }
        deleteToken(verificationToken.getId());
    }

    @Override
    public void deleteToken(Long tokenId) {
        verificationTokenRepository.deleteById(tokenId);
    }

    @Override
    public VerificationToken generateEMailVerificationToken(String email) {
        return verificationTokenRepository.save(
                new VerificationToken(
                        generateSixDigitToken(),
                        LocalDateTime.now().plusMinutes(5L),
                        email
                ));
    }

    private static String generateSixDigitToken() {
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(999999);
        return String.format("%06d", number);
    }

    private VerificationToken generateToken(String email) {
        return verificationTokenRepository.save(
                new VerificationToken(
                        UUID.randomUUID().toString(),
                        LocalDateTime.now().plusHours(1L),
                        email
                )
        );
    }
}
