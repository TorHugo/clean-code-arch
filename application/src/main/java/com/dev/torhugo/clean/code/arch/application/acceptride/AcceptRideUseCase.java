package com.dev.torhugo.clean.code.arch.application.acceptride;

import com.dev.torhugo.clean.code.arch.domain.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.gateway.RideGateway;

import java.util.Objects;

import static com.dev.torhugo.clean.code.arch.domain.utils.RideStatusEnumUtils.ACCEPTED;

public class AcceptRideUseCase {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;
    public AcceptRideUseCase(final RideGateway rideGateway,
                             final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }

    public void execute(final AcceptRideInput input){
        final var ride = this.rideGateway.getRideById(input.rideId());
        if (Objects.isNull(ride))
            throw new DatabaseNotFoundError("Ride not found!");
        final var driver = this.accountGateway.getByAccountId(input.driverId());
        if (Objects.isNull(driver))
            throw new DatabaseNotFoundError("Passenger not found!");
        if (driver.isPassenger())
            throw new InvalidArgumentError("This account not driver!");
        final var acceptedRides = this.rideGateway.getAllRidesWithStatus(driver, ACCEPTED.getDescription());
        if (!acceptedRides.isEmpty())
            throw new InvalidArgumentError("This driver contains unfinished rides!");
        ride.accept(driver.getAccountId());
        this.rideGateway.update(ride);
    }
}
