package com.dev.torhugo.clean.code.arch.application.gateway;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;

import java.util.List;
import java.util.UUID;

public interface RideGateway {
    List<Ride> getAllRidesWithStatus(final UUID accountId, final boolean isPassenger, final String status);
    void save(final Ride ride);
    Ride getRideById(final UUID rideId);
    void update(final Ride ride);
}
