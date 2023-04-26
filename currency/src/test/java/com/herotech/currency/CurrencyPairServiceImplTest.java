package com.herotech.currency;


import com.herotech.data.dtos.CalculateRequest;
import com.herotech.data.entities.CurrencyPair;
import com.herotech.data.enums.Symbol;
import com.herotech.data.repositories.CurrencyPairRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CurrencyPairServiceImpl.class)
class CurrencyPairServiceImplTest {
    @MockBean
    private CurrencyPairRepository currencyPairRepository;

    @Autowired
    private CurrencyPairService currencyPairService;

    @Test
    void calculate() {
        when(currencyPairRepository.findBySymbol(any())).thenReturn(new CurrencyPair(Symbol.USD_NGN, BigDecimal.valueOf(759.81)));
    }
}