package com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAccountResponse(
        @JsonProperty("account_id")
        UUID accountId,
        String name,
        String email,
        String cpf,
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonProperty("is_passenger")
        boolean isPassenger,
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonProperty("is_driver")
        boolean isDriver,
        @JsonProperty("car_plate")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String carPlate,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        LocalDateTime createdAt,
        @JsonProperty("update_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime updateAt
) {
    public static GetAccountResponse from(final GetAccountOutput account){
            return new GetAccountResponse(
                    account.accountId(),
                    account.name(),
                    account.email(),
                    account.cpf(),
                    account.isPassenger(),
                    account.isDriver(),
                    account.carPlate(),
                    account.createdAt(),
                    account.updatedAt());
    }
}
