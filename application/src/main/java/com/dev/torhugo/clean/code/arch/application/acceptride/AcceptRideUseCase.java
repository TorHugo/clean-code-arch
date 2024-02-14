package com.dev.torhugo.clean.code.arch.application.acceptride;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;

import java.util.List;
import java.util.Objects;

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
        final var ride = rideGateway.getRideById(input.rideId());
        validateRide(ride);
        final var account = this.accountGateway.getByAccountId(input.driverId());
        final var acceptedRides = this.rideGateway.getAllRidesWithStatus(account, ACCEPTED.getDescription());
        validateAccount(account, acceptedRides);
        ride.accepted(account.getAccountId());
        this.rideGateway.update(ride);
    }

    private void validateRide(final Ride ride) {
        if (Objects.isNull(ride))
            throw new DatabaseNotFoundError("Ride not found!");
    }

    private void validateAccount(final Account account, final List<Ride> acceptedRides) {
        if (Objects.isNull(account))
            throw new DatabaseNotFoundError("Passenger not found!");
        if (account.isPassenger())
            throw new InvalidArgumentError("This account not driver!");
        if (!acceptedRides.isEmpty())
            throw new InvalidArgumentError("This driver contains unfinished rides!");
    }
}
