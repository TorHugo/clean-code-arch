package com.dev.torhugo.clean.code.arch.domain.entity;

import com.dev.torhugo.clean.code.arch.domain.vo.Coord;

import java.time.LocalDateTime;
import java.util.UUID;

public class Position {

    private final UUID positionId;
    private final UUID rideId;
    private final Coord coord;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Position(final UUID positionId,
                     final UUID rideId,
                     final Double latitude,
                     final Double longitude,
                     final LocalDateTime createdAt,
                     final LocalDateTime updatedAt) {
        this.positionId = positionId;
        this.rideId = rideId;
        this.coord = new Coord(longitude, latitude);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Position create(final UUID rideId,
                                  final Double latitude,
                                  final Double longitude){
        final var positionId = UUID.randomUUID();
        final var createdAt = LocalDateTime.now();
        return new Position(positionId, rideId, latitude, longitude, createdAt, null);
    }

    public static Position restore(final UUID positionId,
                                   final UUID rideId,
                                   final Double latitude,
                                   final Double longitude,
                                   final LocalDateTime createdAt,
                                   final LocalDateTime updatedAt){
        return new Position(positionId, rideId, latitude, longitude, createdAt, updatedAt);
    }

    public UUID getPositionId() {
        return positionId;
    }

    public UUID getRideId() {
        return rideId;
    }

    public Coord getCoord() {
        return coord;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
