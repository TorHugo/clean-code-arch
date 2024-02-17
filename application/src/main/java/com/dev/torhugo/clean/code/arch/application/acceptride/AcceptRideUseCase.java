package com.dev.torhugo.clean.code.arch.application.acceptride;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum.ACCEPTED;

public class AcceptRideUseCase {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;
    public AcceptRideUseCase(final RideGateway rideGateway,
                             final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }

    public void execute(final AcceptRideInput input){
        final var ride = retrieveRide(input.rideId());
        final var driver = retrieveAccount(input.driverId());
        validateActiveRides(driver);
        ride.accept(driver.getAccountId());
        this.rideGateway.update(ride);
    }

    private Ride retrieveRide(final UUID rideId){
        final var actualRide = this.rideGateway.getRideById(rideId);
        if (Objects.isNull(actualRide))
            throw new DatabaseNotFoundError("Ride not found!");
        return actualRide;
    }

    private Account retrieveAccount(final UUID accountId){
        final var account = this.accountGateway.getByAccountId(accountId);
        if (Objects.isNull(account))
            throw new DatabaseNotFoundError("Passenger not found!");
        if (account.isPassenger())
            throw new InvalidArgumentError("This account not driver!");
        return account;
    }

    private void validateActiveRides(final Account driver){
        final var acceptedRides = this.rideGateway.getAllRidesWithStatus(driver, ACCEPTED.getDescription());
        if (!acceptedRides.isEmpty())
            throw new InvalidArgumentError("This driver contains unfinished rides!");
    }
}
