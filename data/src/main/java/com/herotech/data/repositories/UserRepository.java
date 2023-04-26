package com.herotech.data.repositories;

import com.herotech.data.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPasswordResetToken(String passwordResetToken);

    Optional<AppUser> findByEmailVerificationToken(String token);

    boolean existsByEmail(String email);
}
