package com.dev.torhugo.clean.code.arch.domain.account;

import java.util.Optional;
import java.util.UUID;

public interface AccountGateway {
    Optional<Account> getByEmail(final String email);
    void save(final Account account);
    Optional<Account> getByAccountId(final UUID accountId);
}
