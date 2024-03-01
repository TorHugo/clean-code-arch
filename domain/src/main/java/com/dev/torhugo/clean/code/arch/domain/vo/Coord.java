package com.dev.torhugo.clean.code.arch.domain.vo;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

public class Coord {
    private final Double longitude;
    private final Double latitude;

    public Coord(final Double longitude,
                 final Double latitude) {
        if (latitude < -90 || latitude > 90) throw  new InvalidArgumentError("Invalid latitude!");
        if (longitude < -180 || longitude > 180) throw new InvalidArgumentError("Invalid longitude!");
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
