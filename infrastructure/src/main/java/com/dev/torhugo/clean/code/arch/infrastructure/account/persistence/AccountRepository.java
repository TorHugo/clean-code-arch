package com.dev.torhugo.clean.code.arch.infrastructure.account.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.account.models.AccountEntity;

import java.util.Optional;

// Port
public interface AccountRepository {
    Optional<AccountEntity> getByEmail(final String email);

    void save(final AccountEntity account);
}