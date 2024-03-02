package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AcceptRideRequest(
        @JsonProperty("ride_id") UUID rideId,
        @JsonProperty("driver_id") UUID driverId
) {
}
