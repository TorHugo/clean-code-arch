package com.dev.torhugo.clean.code.arch.domain.account;

import java.util.Optional;

public interface AccountGateway {
    Optional<Account> getByEmail(final String email);
    void save(final Account account);
}
