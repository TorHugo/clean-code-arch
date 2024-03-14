package com.dev.torhugo.clean.code.arch.application.finishride;

import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;

import java.util.Objects;
import java.util.UUID;

public class FinishRideUseCase {

    private final RideGateway rideGateway;

    public FinishRideUseCase(final RideGateway rideGateway) {
        this.rideGateway = rideGateway;
    }

    public void execute(final UUID rideId){
        final var ride = rideGateway.getRideById(rideId);
        if (Objects.isNull(ride))
            throw new GatewayNotFoundError("Ride not found!");
        ride.finish();
        this.rideGateway.update(ride);
    }
}
