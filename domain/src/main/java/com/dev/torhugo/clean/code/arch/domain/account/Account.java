package com.dev.torhugo.clean.code.arch.domain.account;

import com.dev.torhugo.clean.code.arch.domain.DomainDefault;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account extends DomainDefault {

    private final UUID accountId;
    private final String name;
    private final String email;
    private final String cpf;
    private final boolean isPassenger;
    private final boolean isDriver;
    private final String carPlate;

    private Account(final UUID accountId,
                    final String name,
                    final String email,
                    final String cpf,
                    final boolean isPassenger,
                    final boolean isDriver,
                    final String carPlate,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt) {
        if (validateRegexName(name)) throw new IllegalArgumentException("Invalid name!");
        if (validateRegexEmail(email)) throw new IllegalArgumentException("Invalid email!");
        if (!validateInvalidCpf(cpf)) throw new IllegalArgumentException("Invalid cpf!");
        if (isDriver && Objects.nonNull(carPlate) && validateRegexCarPlate(carPlate)) throw new IllegalArgumentException("Invalid car plate!");

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
        final var accountId = UUID.randomUUID();
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
}
