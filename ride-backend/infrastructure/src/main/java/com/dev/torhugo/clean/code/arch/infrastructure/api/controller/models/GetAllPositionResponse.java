package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionOutput;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record GetAllPositionResponse(
        @JsonProperty("ride_id") UUID rideId,
        @JsonProperty("positions") List<GetPositionResponse> positions
) {
    public static GetAllPositionResponse from(final UUID rideId, final GetAllPositionOutput allPositions) {
        final var allPositionsResponse = allPositions.lsPositions().stream().map(GetPositionResponse::with).toList();
        return new GetAllPositionResponse(rideId, allPositionsResponse);
    }
}
