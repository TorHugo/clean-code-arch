package com.dev.torhugo.clean.code.arch.infrastructure.ride.models;

import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.database.EntityDefault;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table()
@Entity(name = "ride")
@Getter
@Setter
public class RideEntity {
    @Id
    private UUID rideId;
    private UUID passengerId;
    private UUID driverId;
    private String status;
    private BigDecimal fare;
    private Double distance;
    private Double fromLat;
    private Double fromLong;
    private Double toLat;
    private Double toLong;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private RideEntity(final UUID rideId,
                       final UUID passengerId,
                       final UUID driverId,
                       final String status,
                       final BigDecimal fare,
                       final Double distance,
                       final Double fromLat,
                       final Double fromLong,
                       final Double toLat,
                       final Double toLong,
                       final LocalDateTime createdAt,
                       final LocalDateTime updatedAt) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.status = status;
        this.fare = fare;
        this.distance = distance;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RideEntity() {

    }

    public static RideEntity fromAggregate(final Ride ride){
        return new RideEntity(
                ride.getRideId(),
                ride.getPassengerId(),
                ride.getDriverId(),
                ride.getStatus(),
                ride.getFare(),
                ride.getDistance(),
                ride.getFromLat(),
                ride.getFromLong(),
                ride.getToLat(),
                ride.getToLong(),
                ride.getCreatedAt(),
                ride.getUpdatedAt()
        );
    }

    public static List<Ride> toAggregateList(final List<RideEntity> activesRides) {
        return activesRides.stream().map(RideEntity::toAggregate).toList();
    }

    public static Ride toAggregate(final RideEntity ride) {
        return Ride.restore(
                ride.rideId,
                ride.passengerId,
                ride.driverId,
                ride.fromLat,
                ride.fromLong,
                ride.toLat,
                ride.toLong,
                ride.status,
                ride.fare,
                ride.distance,
                ride.createdAt,
                ride.updatedAt);
    }
}
