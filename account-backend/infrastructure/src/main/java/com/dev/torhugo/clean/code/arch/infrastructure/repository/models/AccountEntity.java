package com.dev.torhugo.clean.code.arch.infrastructure.repository.models;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table()
@Entity(name = "account")
@Getter
@Setter
public class AccountEntity {

    @Id
    private UUID accountId;
    private String name;
    private String email;
    private String cpf;
    @Column(name = "is_passenger")
    private boolean passenger;
    @Column(name = "is_driver")
    private boolean driver;
    private String carPlate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AccountEntity(
            final UUID accountId,
            final String name,
            final String email,
            final String cpf,
            final boolean isPassenger,
            final boolean isDriver,
            final String carPlate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.passenger = isPassenger;
        this.driver = isDriver;
        this.carPlate = carPlate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AccountEntity() {

    }

    public static Account toAggregate(final AccountEntity accountEntity){
        return
            Account.restore(
                accountEntity.accountId,
                accountEntity.name,
                accountEntity.email,
                accountEntity.cpf,
                accountEntity.passenger,
                accountEntity.driver,
                accountEntity.carPlate,
                accountEntity.createdAt,
                accountEntity.updatedAt);
    }

    public static AccountEntity fromAggregate(final Account account){
        return new AccountEntity(
                account.getAccountId(),
                account.getName(),
                account.getEmail(),
                account.getCpf(),
                account.isPassenger(),
                account.isDriver(),
                account.getCarPlate(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
