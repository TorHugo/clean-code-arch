package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

import java.util.Objects;

public class SignUpUseCase {

    private final AccountGateway accountGateway;

    public SignUpUseCase(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public String execute(final SingUpInput input) {
        final var existingAccount = this.accountGateway.getByEmail(input.email());
        if (Objects.nonNull(existingAccount))
            throw new InvalidArgumentError("Account already exists!");
        final var account = Account.create(
                input.name(),
                input.email(),
                input.cpf(),
                input.isPassenger(),
                input.isDriver(),
                input.carPlate()
        );
        this.accountGateway.save(account);
        return account.getAccountId().toString();
    }
}

