package com.herotech.data.dtos;

import com.herotech.data.enums.Symbol;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CalculateRequest {
    @NonNull
    private Symbol symbol;
    @NonNull
    private BigDecimal amount;
    private boolean flip;

}
