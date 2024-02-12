package com.dev.torhugo.clean.code.arch.application.getaccount;

import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;

import java.util.UUID;

public class GetAccountUseCase {

    private final AccountGateway accountGateway;

    public GetAccountUseCase(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public GetAccountOutput execute(final UUID accountId) {
        final var accountOptional = this.accountGateway.getByAccountId(accountId);
        if (accountOptional.isEmpty())
            throw new IllegalArgumentException("Account not found!");
        return GetAccountOutput.from(accountOptional.get());
    }
}
