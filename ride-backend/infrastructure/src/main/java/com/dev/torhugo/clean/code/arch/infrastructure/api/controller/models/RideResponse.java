package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RideResponse(
        @JsonProperty("ride_id") String rideId
) {
    public static RideResponse from(final String rideId){
        return new RideResponse(rideId);
    }
}
