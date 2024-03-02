package com.dev.torhugo.clean.code.arch.infrastructure.gateway.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
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
}
