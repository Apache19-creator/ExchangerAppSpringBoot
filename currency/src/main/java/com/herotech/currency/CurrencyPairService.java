package com.herotech.currency;

import com.herotech.data.dtos.CalculateRequest;
import com.herotech.data.dtos.requests.CurrencyPairRequest;
import com.herotech.data.dtos.responses.CurrencyPairResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CurrencyPairService {
    List<CurrencyPairResponse> getAll();

    String editPair(long rateId, BigDecimal value);

    String addPair(CurrencyPairRequest currencyPairRequest);

    Map<String, Object> calculate(CalculateRequest calculateRequest);
}
