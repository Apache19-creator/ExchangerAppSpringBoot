package com.herotech.currency;

import com.herotech.data.dtos.CalculateRequest;
import com.herotech.data.dtos.requests.CurrencyPairRequest;
import com.herotech.data.dtos.responses.CurrencyPairResponse;
import com.herotech.data.entities.CurrencyPair;
import com.herotech.data.repositories.CurrencyPairRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import static com.herotech.data.enums.Symbol.*;

@Service
@RequiredArgsConstructor
public class CurrencyPairServiceImpl implements CurrencyPairService {

    private final CurrencyPairRepository currencyPairRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CurrencyPairResponse> getAll() {
        return currencyPairRepository.findAll().stream().map(
                pair -> modelMapper.map(pair, CurrencyPairResponse.class)
        ).toList();
    }

    @Override
    public String editPair(long rateId, BigDecimal value) {
        CurrencyPair currencyPair = currencyPairRepository.findById(rateId).orElseThrow();
        currencyPair.setRate(value);
        currencyPairRepository.save(currencyPair);
        return "edited successfully";
    }

    @Override
    public String addPair(CurrencyPairRequest currencyPairRequest) {
        CurrencyPair currencyPair = modelMapper.map(currencyPairRequest, CurrencyPair.class);
        currencyPairRepository.save(currencyPair);
        return "added successfully";
    }

    @Override
    public Map<String, Object> calculate(CalculateRequest calculateRequest) {
        CurrencyPair currencyPair = currencyPairRepository
                .findBySymbol(calculateRequest.getSymbol());
        if (calculateRequest.isFlip()) {
            BigDecimal value = calculateRequest.getAmount().divide(currencyPair.getRate(), RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
            return generateResult(calculateRequest, value, currencyPair.getRate());

        }

        BigDecimal value = currencyPair.getRate().multiply(calculateRequest.getAmount())
                .setScale(2, RoundingMode.HALF_UP);
        return generateResult(calculateRequest, value, currencyPair.getRate());
    }

    private Map<String, Object> generateResult(CalculateRequest request, BigDecimal value, BigDecimal rate) {
        String[] pair = request.getSymbol().toString().split("_");
        DecimalFormat df = new DecimalFormat(",###,###.00");
        if (request.isFlip()) {
            String temp = pair[0];
            pair[0] = pair[1];
            pair[1] = temp;
        }
        String result = df.format(request.getAmount()) + " " + pair[0] + " is " + df.format(value) + " " + pair[1];

        return Map.of(
                "result", result,
                "rate", rate + " " + pair[1] + " per " + pair[0]
        );

    }


    @PostConstruct
    public void initCurrencies() {
        List<CurrencyPair> currencyPairList = List.of(
                new CurrencyPair(GBP_NGN, BigDecimal.valueOf(872.99)),
                new CurrencyPair(USD_NGN, BigDecimal.valueOf(759.81)),
                new CurrencyPair(AUD_NGN, BigDecimal.valueOf(607.16)),
                new CurrencyPair(EUR_NGN, BigDecimal.valueOf(802.72)),
                new CurrencyPair(GBP_AUD, BigDecimal.valueOf(1.86184)),
                new CurrencyPair(USD_AUD, BigDecimal.valueOf(1.4969)),
                new CurrencyPair(EUR_AUD, BigDecimal.valueOf(1.63449)),
                new CurrencyPair(EUR_GBP, BigDecimal.valueOf(0.87995))
        );
        for (CurrencyPair currencyPair : currencyPairList) {
            if (!currencyPairRepository.existsBySymbol(currencyPair.getSymbol())) {
                currencyPairRepository.save(currencyPair);
            }
        }
    }
}
