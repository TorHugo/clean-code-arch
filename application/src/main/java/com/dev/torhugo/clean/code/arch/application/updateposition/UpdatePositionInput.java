package com.dev.torhugo.clean.code.arch.application.updateposition;

import java.util.UUID;

public record UpdatePositionInput(
        UUID rideId,
        Double latitude,
        Double longitude
) {
    public static UpdatePositionInput with(final UUID rideId, final Double latitude, final Double longitude) {
        return new UpdatePositionInput(rideId, latitude, longitude);
    }
}
