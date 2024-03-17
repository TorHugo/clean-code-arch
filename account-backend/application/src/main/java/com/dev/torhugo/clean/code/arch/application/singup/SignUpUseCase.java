package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.enums.MessageEnum;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

import java.util.Objects;

public class SignUpUseCase {

    private final AccountRepository accountRepository;
    private final QueueProducer queueProducer;

    public SignUpUseCase(final AccountRepository accountRepository,
                         final QueueProducer queueProducer) {
        this.accountRepository = accountRepository;
        this.queueProducer = queueProducer;
    }

    public String execute(final SignUpInput input) {
        final var existsAccount = this.accountRepository.getByEmail(input.email());
        if (Objects.nonNull(existsAccount))
            throw new InvalidArgumentError("Account already exists!");
        final var account = Account.create(
                input.name(),
                input.email(),
                input.cpf(),
                input.isPassenger(),
                input.isDriver(),
                input.carPlate()
        );
        this.accountRepository.save(account);
        final var messageWelcome = SignUpMail.with(account.getEmail(), MessageEnum.WELCOME.getMessage());
        this.queueProducer.sendMessage("QUEUE_SIGN_UP_WELCOME", messageWelcome);
        return account.getAccountId().toString();
    }
}

