package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetRideOutput(
        UUID rideId,
        BasicAccountOutput passenger,
        BasicAccountOutput driver,
        String status,
        BigDecimal fare,
        Double distance,
        CoordinatesRequestInfo from,
        CoordinatesRequestInfo to,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetRideOutput from(final Ride ride, final Account passenger) {
        return new GetRideOutput(
                ride.getRideId(),
                BasicAccountOutput.from(passenger),
                null,
                ride.getStatus(),
                ride.getFare(),
                ride.getDistance(),
                CoordinatesRequestInfo.from(ride.getFromLat(), ride.getFromLong()),
                CoordinatesRequestInfo.from(ride.getToLat(), ride.getToLong()),
                ride.getCreatedAt(),
                ride.getUpdatedAt()
        );
    }
}