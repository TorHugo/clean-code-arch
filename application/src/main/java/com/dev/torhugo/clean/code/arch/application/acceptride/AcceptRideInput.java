package com.dev.torhugo.clean.code.arch.application.acceptride;

import java.util.UUID;

public record AcceptRideInput(
        UUID rideId,
        UUID driverId
) {
    public static AcceptRideInput with(final UUID rideId, final UUID driverId) {
        return new AcceptRideInput(rideId, driverId);
    }
}
