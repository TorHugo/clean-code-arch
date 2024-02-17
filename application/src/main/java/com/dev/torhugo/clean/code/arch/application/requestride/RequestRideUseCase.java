package com.dev.torhugo.clean.code.arch.application.requestride;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;
import com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RequestRideUseCase {

    private final AccountGateway accountGateway;
    private final RideGateway rideGateway;

    public RequestRideUseCase(final AccountGateway accountGateway, final RideGateway rideGateway) {
        this.accountGateway = accountGateway;
        this.rideGateway = rideGateway;
    }

    public String execute(final RequestRideInput input){
        final var passenger = retrieveAccount(input.passengerId());
        validateActiveRides(passenger);
        final var actualRide = createRide(input);
        this.rideGateway.save(actualRide);
        return actualRide.getRideId().toString();
    }

    private Ride createRide(final RequestRideInput input) {
        return Ride.create(
                input.passengerId(),
                input.from().latitude(),
                input.from().longitude(),
                input.to().latitude(),
                input.to().longitude()
        );
    }

    private Account retrieveAccount(final UUID accountId){
        final var account = this.accountGateway.getByAccountId(accountId);
        if(Objects.isNull(account))
            throw new DatabaseNotFoundError("Account not found!");
        if (account.isDriver())
            throw new InvalidArgumentError("Account is not passenger!");
        return account;
    }

    private void validateActiveRides(final Account account){
        final var passengerRides= this.rideGateway.getAllRidesWithStatus(account, RideStatusEnum.REQUESTED.getDescription());
        if (!passengerRides.isEmpty())
            throw new InvalidArgumentError("Passenger has an active ride!");
    }
}
