package com.dev.torhugo.clean.code.arch.application.requestride;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import com.dev.torhugo.clean.code.arch.domain.utils.RideStatusEnumUtils;

import java.util.Objects;

public class RequestRideUseCase {

    private final AccountGateway accountGateway;
    private final RideRepository rideRepository;

    public RequestRideUseCase(final AccountGateway accountGateway, final RideRepository rideRepository) {
        this.accountGateway = accountGateway;
        this.rideRepository = rideRepository;
    }

    public String execute(final RequestRideInput input){
        final var passenger = this.accountGateway.getByAccountId(input.passengerId());
        if (passenger.isDriver())
            throw new InvalidArgumentError("Account is not passenger!");
        final var passengerRides= this.rideRepository.getAllRidesWithStatus(passenger.accountId(), true, RideStatusEnumUtils.REQUESTED.getDescription());
        if (!passengerRides.isEmpty())
            throw new InvalidArgumentError("Passenger has an active ride!");
        final var actualRide = Ride.create(
                input.passengerId(),
                input.from().latitude(),
                input.from().longitude(),
                input.to().latitude(),
                input.to().longitude()
        );
        this.rideRepository.save(actualRide);
        return actualRide.getRideId().toString();
    }
}
