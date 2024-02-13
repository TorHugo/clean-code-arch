package com.dev.torhugo.clean.code.arch.infrastructure.ride.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.ride.models.RideEntity;

import java.util.List;
import java.util.UUID;

public interface RideRepository {
    void save(final RideEntity rideEntity);

    List<RideEntity> getActiveRidesByPassengerId(final UUID accountId);
}
