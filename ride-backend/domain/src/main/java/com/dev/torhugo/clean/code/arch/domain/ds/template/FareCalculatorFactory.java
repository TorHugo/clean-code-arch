package com.dev.torhugo.clean.code.arch.domain.ds.template;

import com.dev.torhugo.clean.code.arch.domain.ds.FareCalculator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class FareCalculatorFactory {
    private FareCalculatorFactory(){}
    public static FareCalculator create(final LocalDateTime date){
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
            return new SundayFareCalculator();
        else if (date.getHour() > 22 || date.getHour() < 6)
            return new OvernightFareCalculator();
        else
            return new NormalFareCalculator();
    }
}
