package com.herotech.data.entities;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class VerificationToken {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String token;
    @NonNull
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    @NonNull
    private String email;
}
