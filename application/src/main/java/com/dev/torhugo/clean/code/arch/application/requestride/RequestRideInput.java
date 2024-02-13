package com.dev.torhugo.clean.code.arch.application.requestride;

import java.util.UUID;

public record RequestRideInput(
    UUID passengerId,
    CoordinatesRequestInfo from,
    CoordinatesRequestInfo to
) {
    public static RequestRideInput with(final UUID passengerId, final Double fromLat, final Double fromLong, final Double toLat, final Double toLong){
        return new RequestRideInput(passengerId, CoordinatesRequestInfo.with(fromLat, fromLong), CoordinatesRequestInfo.with(toLat, toLong));
    }
}
