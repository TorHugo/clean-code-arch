package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;

import java.util.Objects;

public class SignUpUseCase {

    private final AccountGateway accountGateway;

    public SignUpUseCase(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public String execute(final SingUpInput input) {
        final var existingAccount = this.accountGateway.getByEmail(input.email());
        if (Objects.nonNull(existingAccount))
            throw new IllegalArgumentException("Account already exists!");
        final var account = createAccount(input);
        this.accountGateway.save(account);
        return account.getAccountId().toString();
    }

    private Account createAccount(final SingUpInput input){
        return Account.create(
                input.name(),
                input.email(),
                input.cpf(),
                input.isPassenger(),
                input.isDriver(),
                input.carPlate()
        );
    }
}

