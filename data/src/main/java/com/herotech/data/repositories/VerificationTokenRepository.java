package com.herotech.data.repositories;

import com.herotech.data.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findVerificationTokenByToken(String token);
}
