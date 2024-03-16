package com.dev.torhugo.clean.code.arch.application.repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;

import java.util.UUID;

public interface AccountRepository {
    Account getByEmail(final String email);
    void save(final Account account);
    Account getByAccountId(final UUID accountId);
}
