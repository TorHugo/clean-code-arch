package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;

public class SignUpUseCase {

    private final AccountGateway accountGateway;

    public SignUpUseCase(final AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public String execute(final CreateSingUpInput input) {
        final var existingAccount = this.accountGateway.getByEmail(input.email());
        if (existingAccount.isPresent())
            throw new IllegalArgumentException("Account already exists!");
        final var account = Account.create(input.name(), input.email(), input.cpf(),
                input.isPassenger(), input.isDriver(), input.carPlate());
        this.accountGateway.save(account);
        return account.getAccountId().toString();
    }
}

