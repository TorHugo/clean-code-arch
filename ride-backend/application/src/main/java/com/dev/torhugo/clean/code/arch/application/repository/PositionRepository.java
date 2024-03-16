package com.dev.torhugo.clean.code.arch.application.repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;

import java.util.List;
import java.util.UUID;

public interface PositionRepository {
    void save(final Position position);
    List<Position> retrieveByRideId(final UUID rideId);
}
