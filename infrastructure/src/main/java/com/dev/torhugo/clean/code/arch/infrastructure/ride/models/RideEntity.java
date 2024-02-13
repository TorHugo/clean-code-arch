package com.dev.torhugo.clean.code.arch.infrastructure.ride.models;

import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.database.EntityDefault;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table()
@Entity(name = "ride")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RideEntity extends EntityDefault {
    @Id
    private UUID rideId;
    private UUID passengerId;
    private String status;
    private Double fromLat;
    private Double fromLong;
    private Double toLat;
    private Double toLong;

    private RideEntity(final UUID rideId,
                       final UUID passengerId,
                       final String status,
                       final Double fromLat,
                       final Double fromLong,
                       final Double toLat,
                       final Double toLong,
                       final LocalDateTime createdAt,
                       final LocalDateTime updatedAt) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.status = status;
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
                ride.getStatus(),
                ride.getFromLat(),
                ride.getFromLong(),
                ride.getToLat(),
                ride.getToLong(),
                ride.getCreatedAt(),
                ride.getUpdatedAt()
        );
    }

    public static List<Ride> toAggregateList(final List<RideEntity> activesRides) {
        return activesRides.stream().map(ride ->
                Ride.restore(
                        ride.rideId,
                        ride.passengerId,
                        ride.fromLat,
                        ride.fromLong,
                        ride.toLat,
                        ride.toLong,
                        ride.status,
                        ride.createdAt,
                        ride.updatedAt))
                .toList();
    }
}
