package com.dev.torhugo.clean.code.arch.application.getaccount;

import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;

import java.util.Objects;
import java.util.UUID;

public class GetAccountUseCase {

    private final AccountRepository accountRepository;

    public GetAccountUseCase(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public GetAccountOutput execute(final UUID accountId) {
        final var accountOptional = this.accountRepository.getByAccountId(accountId);
        if (Objects.isNull(accountOptional))
            throw new GatewayNotFoundError("Account not found!");
        return GetAccountOutput.from(accountOptional);
    }
}
