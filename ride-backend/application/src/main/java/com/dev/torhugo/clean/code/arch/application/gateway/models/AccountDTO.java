package com.dev.torhugo.clean.code.arch.application.gateway.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountDTO(
        UUID accountId,
        String name,
        String email,
        String cpf,
        boolean isPassenger,
        boolean isDriver,
        String carPlate,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
    public static AccountDTO with(final UUID accountId,
                                  final String name,
                                  final String email,
                                  final String cpf,
                                  final boolean isPassenger,
                                  final boolean isDriver,
                                  final String carPlate,
                                  final LocalDateTime createdAt,
                                  final LocalDateTime updatedAt){
        return new AccountDTO(accountId, name, email, cpf, isPassenger, isDriver, carPlate, createdAt, updatedAt);
    }
}
