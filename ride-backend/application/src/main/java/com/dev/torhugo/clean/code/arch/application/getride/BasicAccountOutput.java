package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.application.gateway.dto.AccountDTO;

import java.util.UUID;

public record BasicAccountOutput(
        UUID accountId,
        String name,
        String email,
        boolean isPassenger,
        boolean isDriver,
        String carPlate
) {
    public static BasicAccountOutput from(final AccountDTO passenger) {
        return new BasicAccountOutput(
                passenger.accountId(),
                passenger.name(),
                passenger.email(),
                passenger.isPassenger(),
                passenger.isDriver(),
                passenger.carPlate()
        );
    }
}
