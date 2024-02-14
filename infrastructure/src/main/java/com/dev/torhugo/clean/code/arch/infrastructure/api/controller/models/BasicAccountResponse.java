package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.getride.BasicAccountOutput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BasicAccountResponse(
        @JsonProperty("account_id")
        UUID accountId,
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email,
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonProperty("is_passenger")
        boolean isPassenger,
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonProperty("is_driver")
        boolean isDriver,
        @JsonProperty("car_plate")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String carPlate
) {
    public static BasicAccountResponse from(final BasicAccountOutput passenger) {
        return new BasicAccountResponse(
                passenger.accountId(),
                passenger.name(),
                passenger.email(),
                passenger.isPassenger(),
                passenger.isDriver(),
                passenger.carPlate()
        );
    }
}
