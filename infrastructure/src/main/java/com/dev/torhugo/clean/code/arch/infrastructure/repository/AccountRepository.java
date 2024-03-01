package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;

import java.util.UUID;

// Port
public interface AccountRepository {
    AccountEntity getByEmail(final String email);

    void save(final AccountEntity account);

    AccountEntity getByAccountId(final UUID accountId);
}
