package com.dev.torhugo.clean.code.arch.application.requestride;

import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;
import com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum;

import java.util.Objects;

public class RequestRideUseCase {

    private final AccountGateway accountGateway;
    private final RideGateway rideGateway;

    public RequestRideUseCase(final AccountGateway accountGateway, final RideGateway rideGateway) {
        this.accountGateway = accountGateway;
        this.rideGateway = rideGateway;
    }

    public String execute(final RequestRideInput input){
        final var passenger = this.accountGateway.getByAccountId(input.passengerId());
        if(Objects.isNull(passenger))
            throw new DatabaseNotFoundError("Account not found!");
        if (passenger.isDriver())
            throw new InvalidArgumentError("Account is not passenger!");
        final var passengerRides= this.rideGateway.getAllRidesWithStatus(passenger, RideStatusEnum.REQUESTED.getDescription());
        if (!passengerRides.isEmpty())
            throw new InvalidArgumentError("Passenger has an active ride!");
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
}
