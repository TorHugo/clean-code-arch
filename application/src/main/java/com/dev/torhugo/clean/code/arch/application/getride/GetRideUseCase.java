package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.gateway.RideGateway;

import java.util.Objects;
import java.util.UUID;

public class GetRideUseCase {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;

    public GetRideUseCase(final RideGateway rideGateway, final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }

    public GetRideOutput execute(final UUID rideId) {
        final var ride = this.rideGateway.getRideById(rideId);
        if (Objects.isNull(ride))
            throw new DatabaseNotFoundError("Ride not found!");
        final var passenger = this.accountGateway.getByAccountId(ride.getPassengerId());
        if (Objects.isNull(passenger))
            throw new DatabaseNotFoundError("Passenger not found!");
        if (Objects.nonNull(ride.getDriverId())) {
            final var driver = this.accountGateway.getByAccountId(ride.getDriverId());
            if (Objects.isNull(driver))
                throw new DatabaseNotFoundError("Driver not found!");
            return GetRideOutput.from(ride, passenger, driver);
        }
        return GetRideOutput.from(ride, passenger);
    }
}
