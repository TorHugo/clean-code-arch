package com.dev.torhugo.clean.code.arch.application.getaccount;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAccountOutput(
        UUID accountId,
        String name,
        String email,
        String cpf,
        boolean isPassenger,
        boolean isDriver,
        String carPlate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetAccountOutput from(final Account account){
        return new GetAccountOutput(
                account.getAccountId(),
                account.getName(),
                account.getEmail(),
                account.getCpf(),
                account.isPassenger(),
                account.isDriver(),
                account.getCarPlate(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }
}
