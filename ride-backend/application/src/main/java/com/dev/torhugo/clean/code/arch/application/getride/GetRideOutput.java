package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.vo.Coord;

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
        CoordinatesRequestInfo lastPosition,
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
                CoordinatesRequestInfo.from(ride.getFrom().getLatitude(), ride.getFrom().getLongitude()),
                CoordinatesRequestInfo.from(ride.getTo().getLatitude(), ride.getTo().getLongitude()),
                CoordinatesRequestInfo.from(ride.getLastPosition().getLatitude(), ride.getLastPosition().getLongitude()),
                ride.getCreatedAt(),
                ride.getUpdatedAt()
        );
    }

    public static GetRideOutput from(final Ride ride, final Account passenger, final Account driver) {
        return new GetRideOutput(
                ride.getRideId(),
                BasicAccountOutput.from(passenger),
                BasicAccountOutput.from(driver),
                ride.getStatus(),
                ride.getFare(),
                ride.getDistance(),
                CoordinatesRequestInfo.from(ride.getFrom().getLatitude(), ride.getFrom().getLongitude()),
                CoordinatesRequestInfo.from(ride.getTo().getLatitude(), ride.getTo().getLongitude()),
                CoordinatesRequestInfo.from(ride.getLastPosition().getLatitude(), ride.getLastPosition().getLongitude()),
                ride.getCreatedAt(),
                ride.getUpdatedAt()
        );
    }
}
