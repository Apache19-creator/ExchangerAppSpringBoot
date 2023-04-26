package com.herotech.shared.services.kyc;

import com.herotech.data.dtos.requests.KycRequest;
import com.herotech.data.exceptions.HeroXchangeException;

public interface KycService {
    String addDocument(KycRequest kycRequest) throws HeroXchangeException;
}
