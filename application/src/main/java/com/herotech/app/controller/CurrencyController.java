package com.herotech.app.controller;

import com.herotech.data.dtos.CalculateRequest;
import com.herotech.data.dtos.responses.CurrencyPairResponse;
import com.herotech.currency.CurrencyPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exchange/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyPairService currencyPairService;

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyPairResponse>> getCurrencyPairs() {
        return ResponseEntity.ok(currencyPairService.getAll());
    }

    @PostMapping("/compare")
    public ApiResponse calculate(@RequestBody CalculateRequest calculateRequest){
        return ApiResponse.ok(currencyPairService.calculate(calculateRequest));
    }
}
