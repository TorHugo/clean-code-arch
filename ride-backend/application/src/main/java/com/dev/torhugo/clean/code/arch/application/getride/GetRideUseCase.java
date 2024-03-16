package com.dev.torhugo.clean.code.arch.application.getride;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

import java.util.Objects;
import java.util.UUID;

public class GetRideUseCase {
    private final RideRepository rideRepository;
    private final AccountGateway accountGateway;

    public GetRideUseCase(final RideRepository rideRepository, final AccountGateway accountGateway) {
        this.rideRepository = rideRepository;
        this.accountGateway = accountGateway;
    }

    public GetRideOutput execute(final UUID rideId) {
        final var ride = this.rideRepository.getRideById(rideId);
        final var passenger = this.accountGateway.getByAccountId(ride.getPassengerId());
        if (Objects.nonNull(ride.getDriverId())) {
            final var driver = this.accountGateway.getByAccountId(ride.getDriverId());
            return GetRideOutput.from(ride, passenger, driver);
        }
        return GetRideOutput.from(ride, passenger);
    }
}
