package com.dev.torhugo.clean.code.arch.application.updateposition;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;

import java.util.Objects;

public class UpdatePositionUseCase {

    private final RideGateway rideGateway;
    private final PositionGateway positionGateway;

    public UpdatePositionUseCase(final RideGateway rideGateway,
                                 final PositionGateway positionGateway) {
        this.rideGateway = rideGateway;
        this.positionGateway = positionGateway;
    }

    public void execute(final UpdatePositionInput input){
        final var ride = rideGateway.getRideById(input.rideId());
        if (Objects.isNull(ride))
            throw new GatewayNotFoundError("Ride not found!");
        ride.updatePosition(input.latitude(), input.longitude());
        this.rideGateway.update(ride);
        final var position = Position.create(input.rideId(), input.latitude(), input.longitude());
        this.positionGateway.save(position);
    }
}
