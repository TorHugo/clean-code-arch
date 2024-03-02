package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingUpUseCaseConfig {
    private final AccountGateway accountGateway;
    public SingUpUseCaseConfig(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }
    @Bean
    public SignUpUseCase signUpUseCase() {
        return new SignUpUseCase(accountGateway);
    }
}
