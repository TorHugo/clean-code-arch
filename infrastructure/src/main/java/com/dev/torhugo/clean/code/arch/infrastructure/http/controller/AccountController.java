package com.dev.torhugo.clean.code.arch.infrastructure.http.controller;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SingUpInput;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.GetAccountResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.SingUpResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.SingUpRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.http.AccountAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class AccountController implements AccountAPI {

    private final SignUpUseCase signUpUseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AccountController(final SignUpUseCase signUpUseCase, GetAccountUseCase getAccountUseCase) {
        this.signUpUseCase = Objects.requireNonNull(signUpUseCase);
        this.getAccountUseCase = Objects.requireNonNull(getAccountUseCase);
    }

    @Override
    public ResponseEntity<?> create(final SingUpRequest input) {
        final var singUpInput = SingUpInput.with(input.name(), input.email(), input.cpf(), input.carPlate(), input.isPassenger(), input.isDriver());
        final var singUpOutput = this.signUpUseCase.execute(singUpInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(SingUpResponse.from(singUpOutput));
    }

    @Override
    public ResponseEntity<?> getAccount(final UUID accountId) {
        final var accountRetrieved = this.getAccountUseCase.execute(accountId);
        final var accountResponse = GetAccountResponse.from(accountRetrieved);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }
}
