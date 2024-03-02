package com.dev.torhugo.clean.code.arch.application.singup.mock;

import com.dev.torhugo.clean.code.arch.application.gateway.dto.AccountDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MockDsl {
    default AccountDTO createAccountPassender(final LocalDateTime createdAt,
                                              final LocalDateTime updatedAt){
        return AccountDTO.create(
                UUID.randomUUID(),
                "John Doe",
                "648.808.745-23",
                "john.doe@gmail.com",
                true,
                false,
                "",
                createdAt,
                updatedAt
        );
    }

    default AccountDTO createAccountDriver(final LocalDateTime createdAt,
                                           final LocalDateTime updatedAt){
        return AccountDTO.create(
                UUID.randomUUID(),
                "John Doe",
                "648.808.745-23",
                "john.doe@gmail.com",
                false,
                true,
                "ABC1234",
                createdAt,
                updatedAt
        );
    }
}
