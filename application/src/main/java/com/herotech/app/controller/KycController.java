package com.herotech.app.controller;

import com.herotech.data.dtos.requests.KycRequest;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.shared.services.kyc.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exchange/v1/kyc")
@RequiredArgsConstructor
public class KycController {
    private final KycService kycService;
    @PostMapping
    public ApiResponse uploadDocument(@RequestBody KycRequest kycRequest) throws HeroXchangeException {
        return ApiResponse.ok(kycService.addDocument(kycRequest));
    }
}
