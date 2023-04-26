package com.herotech.data.repositories;

import com.herotech.data.entities.CurrencyPair;
import com.herotech.data.enums.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
    boolean existsBySymbol(Symbol symbol);

    CurrencyPair findBySymbol(Symbol symbol);
}
