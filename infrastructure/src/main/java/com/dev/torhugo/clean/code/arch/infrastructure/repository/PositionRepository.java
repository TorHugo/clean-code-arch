package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;

import java.util.List;
import java.util.UUID;

public interface PositionRepository {
    void save(final PositionEntity positionEntity);

    List<PositionEntity> retrieveAllPositionByRideId(final UUID rideId);
}
