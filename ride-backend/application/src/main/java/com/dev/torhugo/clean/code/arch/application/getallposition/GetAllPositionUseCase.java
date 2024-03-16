package com.dev.torhugo.clean.code.arch.application.getallposition;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;

import java.util.UUID;

public class GetAllPositionUseCase {
    private final PositionRepository positionRepository;

    public GetAllPositionUseCase(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public GetAllPositionOutput execute(final UUID rideId) {
        final var lsPositions = positionRepository.retrieveByRideId(rideId);
        if (lsPositions.isEmpty())
            throw new GatewayNotFoundError("Positions not found!");
        return GetAllPositionOutput.with(rideId, lsPositions);
    }
}
