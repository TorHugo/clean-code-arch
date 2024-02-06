package com.dev.torhugo.clean.code.arch.infrastructure.http.controller;

import com.dev.torhugo.clean.code.arch.application.singup.CreateSingUpInput;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.account.models.CreateSingUpOutput;
import com.dev.torhugo.clean.code.arch.infrastructure.account.models.CreateSingUpRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.http.AccountAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AccountController implements AccountAPI {

    private final SignUpUseCase signUpUseCase;

    public AccountController(final SignUpUseCase signUpUseCase) {
        this.signUpUseCase = Objects.requireNonNull(signUpUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateSingUpRequest input) {
        final var singUpInput = CreateSingUpInput.with(input.name(), input.email(), input.cpf(), input.carPlate(), input.isPassenger(), input.isDriver());
        final var singUpOutput = this.signUpUseCase.execute(singUpInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateSingUpOutput.from(singUpOutput));
    }
}
