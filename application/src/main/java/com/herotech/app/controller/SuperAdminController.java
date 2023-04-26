package com.herotech.app.controller;

import com.herotech.currency.CurrencyPairService;
import com.herotech.data.dtos.requests.CurrencyPairRequest;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.superadmin.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/exchange/v1/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final CurrencyPairService currencyPairService;
    private final SuperAdminService superAdminService;

    @PostMapping("/edit-rate/{rateId}")
    public ResponseEntity<String> editRate(@PathVariable long rateId, @RequestParam BigDecimal value) {
        return ResponseEntity.ok(currencyPairService.editPair(rateId, value));
    }

    @PostMapping("/add-currency-pair")
    public ResponseEntity<String> addPair(@RequestBody CurrencyPairRequest currencyPairRequest) {
        return ResponseEntity.ok(currencyPairService.addPair(currencyPairRequest));
    }

    @PostMapping("/invite-admin/{email}")
    public ResponseEntity<String> inviteAdmin(@PathVariable String email) throws HeroXchangeException {
        return new ResponseEntity<>(superAdminService.inviteAdmin(email), HttpStatus.CREATED);
    }


}
