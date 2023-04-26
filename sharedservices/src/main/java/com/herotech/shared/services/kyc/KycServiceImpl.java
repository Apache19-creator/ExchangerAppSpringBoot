package com.herotech.shared.services.kyc;

import com.herotech.data.dtos.requests.KycRequest;
import com.herotech.data.entities.AppUser;
import com.herotech.data.entities.KycStatus;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {
    private final UserRepository userRepository;


    @Override
    public String addDocument(KycRequest kycRequest) throws HeroXchangeException {
        AppUser appUser = userRepository
                .findByEmail(kycRequest.getEmail()).orElseThrow(() -> new HeroXchangeException("user not found"));
        appUser.setVerificationDocumentUrl(kycRequest.getDocumentUrl());
        appUser.setKycStatus(KycStatus.PENDING);
        userRepository.save(appUser);
        return "Document added Successfully";
    }
}
