package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetRideResponse(
        @JsonProperty("ride_id")
        UUID rideId,
        @JsonProperty("passenger")
        BasicAccountResponse passenger,
        @JsonProperty("driver")
        BasicAccountResponse driver,
        String status,
        BigDecimal fare,
        Double distance,
        @JsonProperty("from")
        CoordinatesInfo from,
        @JsonProperty("to")
        CoordinatesInfo to,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        LocalDateTime createdAt,
        @JsonProperty("update_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime updateAt
        ) {
    public static GetRideResponse from(final GetRideOutput ride) {
        return new GetRideResponse(
                ride.rideId(),
                BasicAccountResponse.from(ride.passenger()),
                null,
                ride.status(),
                ride.fare(),
                ride.distance(),
                CoordinatesInfo.from(ride.from()),
                CoordinatesInfo.from(ride.to()),
                ride.createdAt(),
                ride.updatedAt()
        );
    }
}
