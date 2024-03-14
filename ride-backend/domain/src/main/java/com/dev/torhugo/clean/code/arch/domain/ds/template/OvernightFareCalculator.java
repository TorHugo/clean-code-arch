package com.dev.torhugo.clean.code.arch.domain.ds.template;

import com.dev.torhugo.clean.code.arch.domain.ds.FareCalculator;

import java.math.BigDecimal;

public class OvernightFareCalculator extends FareCalculator {
    private static final BigDecimal VALUE_FARE = new BigDecimal("3.9");
    @Override
    public BigDecimal getFare() {
        return VALUE_FARE;
    }
}
