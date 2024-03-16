package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAccountUseCaseConfig {
    private final AccountRepository accountRepository;
    public GetAccountUseCaseConfig(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Bean
    public GetAccountUseCase getAccountUseCase() {
        return new GetAccountUseCase(accountRepository);
    }
}
