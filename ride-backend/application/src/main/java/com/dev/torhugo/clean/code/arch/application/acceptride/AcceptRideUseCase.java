package com.dev.torhugo.clean.code.arch.application.acceptride;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

import java.util.Objects;

import static com.dev.torhugo.clean.code.arch.domain.utils.RideStatusEnumUtils.ACCEPTED;

public class AcceptRideUseCase {
    private final RideRepository rideRepository;
    private final AccountGateway accountGateway;
    public AcceptRideUseCase(final RideRepository rideRepository,
                             final AccountGateway accountGateway) {
        this.rideRepository = rideRepository;
        this.accountGateway = accountGateway;
    }

    public void execute(final AcceptRideInput input){
        final var ride = this.rideRepository.getRideById(input.rideId());
        if (Objects.isNull(ride))
            throw new GatewayNotFoundError("Ride not found!");
        final var driver = this.accountGateway.getByAccountId(input.driverId());
        if (Objects.isNull(driver))
            throw new GatewayNotFoundError("Passenger not found!");
        if (driver.isPassenger())
            throw new InvalidArgumentError("This account not driver!");
        final var acceptedRides = this.rideRepository.getAllRidesWithStatus(driver.accountId(), false, ACCEPTED.getDescription());
        if (!acceptedRides.isEmpty())
            throw new InvalidArgumentError("This driver contains unfinished rides!");
        ride.accept(driver.accountId());
        this.rideRepository.update(ride);
    }
}
