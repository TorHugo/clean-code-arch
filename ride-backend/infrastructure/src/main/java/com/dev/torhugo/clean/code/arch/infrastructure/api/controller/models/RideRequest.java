package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RideRequest(
        @JsonProperty("passenger_id") UUID passengerId,
        @JsonProperty("from") CoordinatesInfo from,
        @JsonProperty("to") CoordinatesInfo to
) {
}
