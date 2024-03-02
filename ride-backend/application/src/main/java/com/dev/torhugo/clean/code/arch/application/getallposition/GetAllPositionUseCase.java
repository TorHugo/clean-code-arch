package com.dev.torhugo.clean.code.arch.application.getallposition;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;

import java.util.UUID;

public class GetAllPositionUseCase {
    private final PositionGateway positionGateway;

    public GetAllPositionUseCase(final PositionGateway positionGateway) {
        this.positionGateway = positionGateway;
    }

    public GetAllPositionOutput execute(final UUID rideId) {
        final var lsPositions = positionGateway.retrieveByRideId(rideId);
        if (lsPositions.isEmpty())
            throw new GatewayNotFoundError("Positions not found!");
        return GetAllPositionOutput.with(rideId, lsPositions);
    }
}
