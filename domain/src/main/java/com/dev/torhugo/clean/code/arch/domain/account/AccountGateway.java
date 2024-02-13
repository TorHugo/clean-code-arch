package com.dev.torhugo.clean.code.arch.domain.account;

import java.util.UUID;

public interface AccountGateway {
    Account getByEmail(final String email);
    void save(final Account account);
    Account getByAccountId(final UUID accountId);
}
