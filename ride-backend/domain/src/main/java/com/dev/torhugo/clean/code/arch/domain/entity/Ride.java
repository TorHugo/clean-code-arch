package com.dev.torhugo.clean.code.arch.domain.entity;

import com.dev.torhugo.clean.code.arch.domain.ds.DistanceCalculator;
import com.dev.torhugo.clean.code.arch.domain.ds.template.FareCalculatorFactory;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.vo.Coord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.utils.RideStatusEnumUtils.*;

public class Ride {
    private final UUID rideId;
    private final UUID passengerId;
    private UUID driverId;
    private final Coord to;
    private final Coord from;
    private Coord lastPosition;
    private String status;
    private BigDecimal fare;
    private Double distance;
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
                 final Double lastLat,
                 final Double lastLong,
                 final BigDecimal fare,
                 final Double distance,
                 final LocalDateTime createdAt,
                 final LocalDateTime updatedAt) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.from = new Coord(fromLong, fromLat);
        this.to = new Coord(toLong, toLat);
        this.lastPosition = new Coord(lastLong, lastLat);
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
        final var rideId = UUID.randomUUID();
        final var status = REQUESTED.getDescription();
        final var createdAt = LocalDateTime.now();
        return new Ride(rideId, passengerId, null, fromLat, fromLong, toLat, toLong, status, fromLat, fromLong, BigDecimal.ZERO, null, createdAt, null);
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
                               final Double lastLat,
                               final Double lastLong,
                               final Double distance,
                               final LocalDateTime createdAt,
                               final LocalDateTime updatedAt) {
        return new Ride(rideId, passengerId, driverId, fromLat, fromLong, toLat, toLong, status, lastLat, lastLong, fare, distance, createdAt, updatedAt);
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

    public void updatePosition(final Double latitude,
                               final Double longitude) {
        if (!Objects.equals(this.status, IN_PROGRESS.getDescription()))
            throw new InvalidArgumentError("Could not update position!");
        final var newLastPosition = new Coord(longitude, latitude);
        if (Objects.isNull(this.distance))
            this.distance = DistanceCalculator.calculate(this.lastPosition, newLastPosition);
        else
            this.distance += DistanceCalculator.calculate(this.lastPosition, newLastPosition);
        this.lastPosition = newLastPosition;
        this.updatedAt = LocalDateTime.now();
    }

    public void finish() {
        if (!Objects.equals(this.status, IN_PROGRESS.getDescription()))
            throw new InvalidArgumentError("Could not update position!");
        this.status = COMPLETED.getDescription();
        this.fare = FareCalculatorFactory
                .create(this.createdAt)
                .calculate(this.distance);
    }

    public UUID getRideId() {
        return rideId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public Coord getTo() {
        return to;
    }

    public Coord getFrom() {
        return from;
    }

    public Coord getLastPosition() {
        return lastPosition;
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
