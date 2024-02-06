package com.dev.torhugo.clean.code.arch.infrastructure.account;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.infrastructure.account.persistence.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountPostgresGateway implements AccountGateway {

    private final AccountRepository accountRepository;

    public AccountPostgresGateway(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getByEmail(final String email) {
        return this.accountRepository.getByEmail(email);
    }

    @Override
    public void save(final Account account) {
        this.accountRepository.save(account);
    }
}
