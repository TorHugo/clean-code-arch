package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.domain.account.Account;

import java.util.UUID;

public record BasicAccountOutput(
        UUID accountId,
        String name,
        String email,
        boolean isPassenger,
        boolean isDriver,
        String carPlate
) {
    public static BasicAccountOutput from(final Account passenger) {
        return new BasicAccountOutput(
                passenger.getAccountId(),
                passenger.getName(),
                passenger.getEmail(),
                passenger.isPassenger(),
                passenger.isDriver(),
                passenger.getCarPlate()
        );
    }
}
