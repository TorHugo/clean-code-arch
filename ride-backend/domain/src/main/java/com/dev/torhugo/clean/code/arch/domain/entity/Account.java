package com.dev.torhugo.clean.code.arch.domain.entity;

import com.dev.torhugo.clean.code.arch.domain.vo.CarPlate;
import com.dev.torhugo.clean.code.arch.domain.vo.Cpf;
import com.dev.torhugo.clean.code.arch.domain.vo.Email;
import com.dev.torhugo.clean.code.arch.domain.vo.Name;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account {

    private final UUID accountId;
    private final Name name;
    private final Email email;
    private final Cpf cpf;
    private final boolean isPassenger;
    private final boolean isDriver;
    private final CarPlate carPlate;
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
        this.accountId = accountId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.cpf = new Cpf(cpf);
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
        this.carPlate = new CarPlate(carPlate);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(final String name,
                                 final String email,
                                 final String cpf,
                                 final boolean isPassenger,
                                 final boolean isDriver,
                                 final String carPlate) {
        final var accountId = UUID.randomUUID();
        final var createdAt = LocalDateTime.now();
        return new Account(
                accountId,
                name,
                email,
                cpf,
                isPassenger,
                isDriver,
                carPlate,
                createdAt,
                null
        );
    }

    public static Account restore(final UUID accountId,
                                  final String name,
                                  final String email,
                                  final String cpf,
                                  final boolean isPassenger,
                                  final boolean isDriver,
                                  final String carPlate,
                                  final LocalDateTime createdAt,
                                  final LocalDateTime updatedAt) {
        return new Account(
                accountId,
                name,
                email,
                cpf,
                isPassenger,
                isDriver,
                carPlate,
                createdAt,
                updatedAt
        );
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getCpf() {
        return this.cpf.getValue();
    }

    public String getCarPlate() {
        return this.carPlate.getValue();
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
