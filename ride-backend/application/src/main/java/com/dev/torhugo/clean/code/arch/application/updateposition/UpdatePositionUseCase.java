package com.dev.torhugo.clean.code.arch.application.updateposition;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

import java.util.Objects;

public class UpdatePositionUseCase {

    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;

    public UpdatePositionUseCase(final RideRepository rideRepository,
                                 final PositionRepository positionRepository) {
        this.rideRepository = rideRepository;
        this.positionRepository = positionRepository;
    }

    public void execute(final UpdatePositionInput input){
        final var ride = rideRepository.getRideById(input.rideId());
        ride.updatePosition(input.latitude(), input.longitude());
        this.rideRepository.save(ride);
        final var position = Position.create(input.rideId(), input.latitude(), input.longitude());
        this.positionRepository.save(position);
    }
}
