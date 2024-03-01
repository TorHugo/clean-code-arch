package com.dev.torhugo.clean.code.arch.domain.gateway;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;

import java.util.List;
import java.util.UUID;

public interface PositionGateway {
    void save(final Position position);
    List<Position> retrieveByRideId(final UUID rideId);
}
