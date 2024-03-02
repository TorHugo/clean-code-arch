package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAccountUseCaseConfig {
    private final AccountGateway accountGateway;
    public GetAccountUseCaseConfig(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }
    @Bean
    public GetAccountUseCase getAccountUseCase() {
        return new GetAccountUseCase(accountGateway);
    }
}
