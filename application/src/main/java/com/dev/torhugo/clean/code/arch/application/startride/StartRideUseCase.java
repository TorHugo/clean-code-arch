package com.dev.torhugo.clean.code.arch.application.startride;

import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;

import java.util.Objects;
import java.util.UUID;

public class StartRideUseCase {
    private final RideGateway rideGateway;

    public StartRideUseCase(final RideGateway rideGateway) {
        this.rideGateway = rideGateway;
    }

    public void execute(final UUID rideId){
        final var ride = rideGateway.getRideById(rideId);
        validateRide(ride);
        ride.start();
        this.rideGateway.update(ride);
    }

    private void validateRide(final Ride ride) {
        if (Objects.isNull(ride))
            throw new DatabaseNotFoundError("Ride not found!");
    }
}
