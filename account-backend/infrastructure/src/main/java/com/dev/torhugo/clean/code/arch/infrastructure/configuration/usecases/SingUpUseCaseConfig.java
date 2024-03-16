package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingUpUseCaseConfig {
    private final AccountRepository accountRepository;
    private final QueueProducer queueProducer;
    public SingUpUseCaseConfig(final AccountRepository accountRepository,
                               final QueueProducer queueProducer) {
        this.accountRepository = accountRepository;
        this.queueProducer = queueProducer;
    }
    @Bean
    public SignUpUseCase signUpUseCase() {
        return new SignUpUseCase(accountRepository, queueProducer);
    }
}
