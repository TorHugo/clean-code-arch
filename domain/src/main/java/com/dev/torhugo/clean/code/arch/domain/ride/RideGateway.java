package com.dev.torhugo.clean.code.arch.domain.ride;

import java.util.List;
import java.util.UUID;

public interface RideGateway {
    List<Ride> getActiveRidesByPassengerId(final UUID accountId);
    void save(final Ride ride);
    Ride getRideById(final UUID rideId);
}
