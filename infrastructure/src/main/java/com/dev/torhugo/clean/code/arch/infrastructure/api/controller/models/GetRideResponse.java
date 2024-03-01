package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.getride.BasicAccountOutput;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record GetRideResponse(
        @JsonProperty("ride_id")
        UUID rideId,
        @JsonProperty("passenger")
        BasicAccountResponse passenger,
        @JsonProperty("driver")
        BasicAccountResponse driver,
        @JsonProperty("status")
        String status,
        @JsonProperty("fare")
        BigDecimal fare,
        @JsonProperty("distance")
        Double distance,
        @JsonProperty("from")
        CoordinatesInfo from,
        @JsonProperty("to")
        CoordinatesInfo to,
        @JsonProperty("last_position")
        CoordinatesInfo lastPosition,
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
                Objects.isNull(ride.driver()) ? null : BasicAccountResponse.from(ride.driver()),
                ride.status(),
                ride.fare(),
                ride.distance(),
                CoordinatesInfo.from(ride.from()),
                CoordinatesInfo.from(ride.to()),
                CoordinatesInfo.from(ride.lastPosition()),
                ride.createdAt(),
                ride.updatedAt()
        );
    }
}
