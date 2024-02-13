package com.dev.torhugo.clean.code.arch.application.requestride;

public record CoordinatesRequestInfo(
        Double latitude,
        Double longitude
) {
    public static CoordinatesRequestInfo with(final Double latitude, final Double longitude) {
        return new CoordinatesRequestInfo(latitude, longitude);
    }
}
