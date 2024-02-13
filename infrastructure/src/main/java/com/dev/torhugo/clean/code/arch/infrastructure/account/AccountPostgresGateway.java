package com.dev.torhugo.clean.code.arch.infrastructure.account;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.infrastructure.account.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.account.persistence.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountPostgresGateway implements AccountGateway {

    private final AccountRepository accountRepository;

    public AccountPostgresGateway(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getByEmail(final String email) {
        final var accountEntity = this.accountRepository.getByEmail(email);
        return Objects.isNull(accountEntity) ? null : AccountEntity.toAggregate(accountEntity);
    }

    @Override
    public void save(final Account account) {
        final var accountEntity = AccountEntity.fromAggregate(account);
        this.accountRepository.save(accountEntity);
    }

    @Override
    public Account getByAccountId(final UUID accountId) {
        final var accountEntity = this.accountRepository.getByAccountId(accountId);
        return Objects.isNull(accountEntity) ? null : AccountEntity.toAggregate(accountEntity);
    }
}
