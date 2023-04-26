package com.herotech.data.dtos.responses;

import com.herotech.data.enums.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CurrencyPairResponse {
    private Long id;
    private Symbol symbol;
    private double rate;
}
