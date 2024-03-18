package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaRepository repository;
    @Override
    public Account getByEmail(final String email) {
        final var accountEntity = this.repository.findByEmail(email);
        return accountEntity.map(AccountEntity::toAggregate)
                .orElse(null);
    }

    @Override
    public void save(final Account account) {
        final var accountEntity = AccountEntity.fromAggregate(account);
        this.repository.save(accountEntity);
    }

    @Override
    public Account getByAccountId(final UUID accountId) {
        final var accountEntity = this.repository.findById(accountId);
        return accountEntity.map(AccountEntity::toAggregate)
                .orElseThrow(() -> new RepositoryNotFoundError("Account not found!"));
    }
}
