package com.dev.torhugo.clean.code.arch.application.startride;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

import java.util.Objects;
import java.util.UUID;

public class StartRideUseCase {
    private final RideRepository rideRepository;

    public StartRideUseCase(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void execute(final UUID rideId){
        final var ride = rideRepository.getRideById(rideId);
        ride.start();
        this.rideRepository.save(ride);
    }
}
