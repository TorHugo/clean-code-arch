package com.dev.torhugo.clean.code.arch.domain.account;

import com.dev.torhugo.clean.code.arch.domain.Validator;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.utils.IdentifierUtils.generateIdentifier;

public class Account extends Validator {

    private final UUID accountId;
    private final String name;
    private final String email;
    private final String cpf;
    private final boolean isPassenger;
    private final boolean isDriver;
    private final String carPlate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Account(final UUID accountId,
                    final String name,
                    final String email,
                    final String cpf,
                    final boolean isPassenger,
                    final boolean isDriver,
                    final String carPlate,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt) {
        if (validateRegexName(name)) throw new InvalidArgumentError("Invalid name!");
        if (validateRegexEmail(email)) throw new InvalidArgumentError("Invalid email!");
        if (!validateInvalidCpf(cpf)) throw new InvalidArgumentError("Invalid cpf!");
        if (isDriver && Objects.nonNull(carPlate) && validateRegexCarPlate(carPlate)) throw new InvalidArgumentError("Invalid car plate!");

        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
        this.carPlate = carPlate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(final String name, final String email, final String cpf, final boolean isPassenger, final boolean isDriver, final String carPlate) {
        final var accountId = generateIdentifier();
        final var createdAt = LocalDateTime.now();
        return new Account(accountId, name, email, cpf, isPassenger, isDriver, carPlate, createdAt, null);
    }

    public static Account restore(final UUID accountId, final String name, final String email, final String cpf, final boolean isPassenger, final boolean isDriver, final String carPlate, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        return new Account(accountId, name, email, cpf, isPassenger, isDriver, carPlate, createdAt, updatedAt);
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public Boolean isPassenger() {
        return isPassenger;
    }

    public Boolean isDriver() {
        return isDriver;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
