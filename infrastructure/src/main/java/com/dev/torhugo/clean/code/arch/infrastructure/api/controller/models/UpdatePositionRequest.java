package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UpdatePositionRequest(
        @JsonProperty("ride_id") UUID rideId,
        Double latitude,
        Double longitude
) {
}
