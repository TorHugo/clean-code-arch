package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpInput;
import com.dev.torhugo.clean.code.arch.infrastructure.api.AccountAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAccountResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class AccountController implements AccountAPI {

    @Value("${integration.queue.account.default}")
    private String queueAsync;

    private final SignUpUseCase signUpUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final QueueProducer queueProducer;

    public AccountController(final SignUpUseCase signUpUseCase,
                             final GetAccountUseCase getAccountUseCase,
                             final QueueProducer queueProducer) {
        this.signUpUseCase = Objects.requireNonNull(signUpUseCase);
        this.getAccountUseCase = Objects.requireNonNull(getAccountUseCase);
        this.queueProducer = Objects.requireNonNull(queueProducer);
    }

    @Override
    public SingUpResponse create(final SingUpRequest input) {
        final var singUpInput = SignUpInput.with(input.name(), input.email(), input.cpf(), input.carPlate(), input.isPassenger(), input.isDriver());
        final var singUpOutput = this.signUpUseCase.execute(singUpInput);
        return SingUpResponse.from(singUpOutput);
    }

    @Override
    public void signupAsync(final SingUpRequest input) {
        this.queueProducer.sendMessage(queueAsync, input);
    }

    @Override
    public GetAccountResponse getAccount(final UUID accountId) {
        final var accountRetrieved = this.getAccountUseCase.execute(accountId);
        return GetAccountResponse.from(accountRetrieved);
    }
}
