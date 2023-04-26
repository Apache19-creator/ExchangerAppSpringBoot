package com.herotech.data.entities;

import com.herotech.data.enums.Role;
import com.herotech.data.repositories.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String profileImageUrl;
    //    private Gender gender;
    private LocalDate dateOfBirth;
    //    private Language languagePreference;
//    @ElementCollection
//    private List<Currency> favouriteCurrencies;
    private boolean isVerified;
    private KycStatus kycStatus = KycStatus.NOT_STARTED;
    private String pin;
    //    @OneToMany
//    private List<BankDetail> beneficiaries;
//    @OneToMany
//    private List<BankDetail> bankDetails;
//    @OneToOne
//    private XchangerWallet xchangerWallet;
//    @OneToMany
//    private List<Notification> notifications;
//    private List<Card> cards;
    private Role role;
    private boolean enabled = true;
    private String passwordResetToken;
    private String emailVerificationToken;
    private String verificationDocumentUrl;


    public AppUser(Long id) {
        this.id = id;
    }

    public AppUser(String email, String password) {
        this(email);
        this.password = password;
    }

    public AppUser(String email) {
        this.email = email;
    }

}
