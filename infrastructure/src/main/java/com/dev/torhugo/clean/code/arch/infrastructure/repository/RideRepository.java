package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.RideEntity;

import java.util.List;
import java.util.UUID;

public interface RideRepository {
    void save(final RideEntity rideEntity);
    List<RideEntity> getAllRidesWithStatus(final AccountEntity account, final String status);
    RideEntity getRideById(final UUID rideId);
    void update(final RideEntity rideEntity);
}
