package com.dev.torhugo.clean.code.arch.application.getaccount;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;

import java.util.Objects;
import java.util.UUID;

public class GetAccountUseCase {

    private final AccountGateway accountGateway;

    public GetAccountUseCase(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public GetAccountOutput execute(final UUID accountId) {
        final var accountOptional = this.accountGateway.getByAccountId(accountId);
        if (Objects.isNull(accountOptional))
            throw new GatewayNotFoundError("Account not found!");
        return GetAccountOutput.from(accountOptional);
    }
}
