package com.dev.torhugo.clean.code.arch.infrastructure.messaging.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ProcessPaymentInput(
        @JsonProperty("ride_id")
        UUID rideId
) {
}
