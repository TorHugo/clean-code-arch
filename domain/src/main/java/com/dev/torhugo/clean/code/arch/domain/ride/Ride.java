package com.dev.torhugo.clean.code.arch.domain.ride;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.utils.IdentifierUtils.generateIdentifier;
import static com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum.REQUESTED;

public class Ride {
    private final UUID rideId;
    private final UUID passengerId;
    private final Double fromLat;
    private final Double fromLong;
    private final Double toLat;
    private final Double toLong;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Ride(final UUID rideId,
                 final UUID passengerId,
                 final Double fromLat,
                 final Double fromLong,
                 final Double toLat,
                 final Double toLong,
                 final String status,
                 final LocalDateTime createdAt,
                 final LocalDateTime updatedAt) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Ride create(final UUID passengerId,
                              final Double fromLat,
                              final Double fromLong,
                              final Double toLat,
                              final Double toLong) {
        final var rideId = generateIdentifier();
        final var createdAt = LocalDateTime.now();
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, REQUESTED.name(), createdAt, null);
    }

    public static Ride restore(final UUID rideId,
                               final UUID passengerId,
                               final Double fromLat,
                               final Double fromLong,
                               final Double toLat,
                               final Double toLong,
                               final String status,
                               final LocalDateTime createdAt,
                               final LocalDateTime updatedAt) {
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, status, createdAt, updatedAt);
    }

    public UUID getRideId() {
        return rideId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public Double getFromLat() {
        return fromLat;
    }

    public Double getFromLong() {
        return fromLong;
    }

    public Double getToLat() {
        return toLat;
    }

    public Double getToLong() {
        return toLong;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
