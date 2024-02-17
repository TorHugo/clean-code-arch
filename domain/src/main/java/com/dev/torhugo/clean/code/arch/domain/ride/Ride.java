package com.dev.torhugo.clean.code.arch.domain.ride;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum.*;
import static com.dev.torhugo.clean.code.arch.domain.utils.IdentifierUtils.generateIdentifier;

public class Ride {
    private final UUID rideId;
    private final UUID passengerId;
    private UUID driverId;
    private final Double fromLat;
    private final Double fromLong;
    private final Double toLat;
    private final Double toLong;
    private String status;
    private final BigDecimal fare;
    private final Double distance;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Ride(final UUID rideId,
                 final UUID passengerId,
                 final UUID driverId,
                 final Double fromLat,
                 final Double fromLong,
                 final Double toLat,
                 final Double toLong,
                 final String status,
                 final BigDecimal fare,
                 final Double distance,
                 final LocalDateTime createdAt,
                 final LocalDateTime updatedAt) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.status = status;
        this.fare = fare;
        this.distance = distance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Ride create(final UUID passengerId,
                              final Double fromLat,
                              final Double fromLong,
                              final Double toLat,
                              final Double toLong) {
        final var rideId = generateIdentifier();
        final var status = REQUESTED.getDescription();
        final var createdAt = LocalDateTime.now();
        return new Ride(rideId, passengerId, null, fromLat, fromLong, toLat, toLong, status, null, null, createdAt, null);
    }

    public static Ride restore(final UUID rideId,
                               final UUID passengerId,
                               final UUID driverId,
                               final Double fromLat,
                               final Double fromLong,
                               final Double toLat,
                               final Double toLong,
                               final String status,
                               final BigDecimal fare,
                               final Double distance,
                               final LocalDateTime createdAt,
                               final LocalDateTime updatedAt) {
        return new Ride(rideId, passengerId, driverId, fromLat, fromLong, toLat, toLong, status, fare, distance, createdAt, updatedAt);
    }

    public void accept(final UUID driverId){
        if (!Objects.equals(this.status, REQUESTED.getDescription()))
            throw new InvalidArgumentError("Invalid status!");

        this.driverId = driverId;
        this.status = ACCEPTED.getDescription();
        this.updatedAt = LocalDateTime.now();
    }

    public void start(){
        if (!Objects.equals(this.status, ACCEPTED.getDescription()))
            throw new InvalidArgumentError("Invalid status!");

        this.status = IN_PROGRESS.getDescription();
        this.updatedAt = LocalDateTime.now();
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

    public BigDecimal getFare() {
        return fare;
    }

    public Double getDistance() {
        return distance;
    }

    public UUID getDriverId() {
        return driverId;
    }
}
