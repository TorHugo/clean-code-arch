package com.dev.torhugo.clean.code.arch.infrastructure.account.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSingUpRequest(
        String name,
        String email,
        String cpf,
        @JsonProperty("car_plate")
        String carPlate,
        @JsonProperty("is_passenger")
        boolean isPassenger,
        @JsonProperty("is_driver")
        boolean isDriver
){
}
