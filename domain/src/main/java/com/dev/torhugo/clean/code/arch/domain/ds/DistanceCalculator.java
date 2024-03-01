package com.dev.torhugo.clean.code.arch.domain.ds;

import com.dev.torhugo.clean.code.arch.domain.vo.Coord;

public class DistanceCalculator {
    private DistanceCalculator() {
    }
    public static double calculate(final Coord from, final Coord to) {
        final double earthRadius = 6371;
        final double degreesToRadians = Math.PI / 180;
        double deltaLat = (to.getLatitude() - from.getLatitude()) * degreesToRadians;
        double deltaLon = (to.getLongitude() - from.getLongitude()) * degreesToRadians;
        double a =
                Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                        Math.cos(from.getLatitude() * degreesToRadians) *
                                Math.cos(to.getLatitude() * degreesToRadians) *
                                Math.sin(deltaLon / 2) *
                                Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(earthRadius * c);
    }
}
