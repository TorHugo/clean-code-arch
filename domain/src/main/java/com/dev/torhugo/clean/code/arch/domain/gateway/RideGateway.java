package com.dev.torhugo.clean.code.arch.domain.gateway;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;

import java.util.List;
import java.util.UUID;

public interface RideGateway {
    List<Ride> getAllRidesWithStatus(final Account account, final String status);
    void save(final Ride ride);
    Ride getRideById(final UUID rideId);
    void update(final Ride ride);
}