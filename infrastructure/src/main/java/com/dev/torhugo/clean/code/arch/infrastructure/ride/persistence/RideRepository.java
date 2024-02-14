package com.dev.torhugo.clean.code.arch.infrastructure.ride.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.account.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.ride.models.RideEntity;

import java.util.List;
import java.util.UUID;

public interface RideRepository {
    void save(final RideEntity rideEntity);
    List<RideEntity> getAllRidesWithStatus(final AccountEntity account, final String status);
    RideEntity getRideById(final UUID rideId);
    void update(final RideEntity rideEntity);
}
