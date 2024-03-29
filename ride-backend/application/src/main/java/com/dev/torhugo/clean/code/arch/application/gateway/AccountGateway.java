package com.dev.torhugo.clean.code.arch.application.gateway;

import com.dev.torhugo.clean.code.arch.application.gateway.models.AccountDTO;

import java.util.UUID;

public interface AccountGateway {
    AccountDTO getByAccountId(final UUID accountId);
}
