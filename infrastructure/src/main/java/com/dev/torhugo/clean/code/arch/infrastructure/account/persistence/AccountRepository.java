package com.dev.torhugo.clean.code.arch.infrastructure.account.persistence;

import com.dev.torhugo.clean.code.arch.domain.account.Account;

import java.util.Optional;

// Port
public interface AccountRepository {
    Optional<Account> getByEmail(final String email);

    void save(final Account account);
}
