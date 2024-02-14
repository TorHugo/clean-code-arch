package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;

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
        final var account = this.accountGateway.getByAccountId(ride.getPassengerId());
        if (Objects.isNull(account))
            throw new DatabaseNotFoundError("Passenger not found!");
        return GetRideOutput.from(ride, account);
    }
}
