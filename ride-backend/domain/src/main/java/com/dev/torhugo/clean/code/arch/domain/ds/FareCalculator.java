package com.dev.torhugo.clean.code.arch.domain.ds;

import java.math.BigDecimal;

public abstract class FareCalculator {
    public BigDecimal calculate(final Double distance){
        final var convertedDistance = BigDecimal.valueOf(distance);
        return convertedDistance.multiply(getFare());
    }
    public abstract BigDecimal getFare();
}
