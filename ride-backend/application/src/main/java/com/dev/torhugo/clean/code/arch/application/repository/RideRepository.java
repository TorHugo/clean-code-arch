package com.dev.torhugo.clean.code.arch.application.repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Ride;

import java.util.List;
import java.util.UUID;

public interface RideRepository {
    List<Ride> getAllRidesWithStatus(final UUID accountId, final boolean isPassenger, final String status);
    void save(final Ride ride);
    Ride getRideById(final UUID rideId);
    void update(final Ride ride);
}
