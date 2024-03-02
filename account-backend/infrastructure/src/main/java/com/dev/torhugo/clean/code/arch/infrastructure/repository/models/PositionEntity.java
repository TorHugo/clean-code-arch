package com.dev.torhugo.clean.code.arch.infrastructure.repository.models;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table()
@Entity(name = "position")
@Getter
@Setter
public class PositionEntity {

    @Id
    private UUID positionId;
    private UUID rideId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PositionEntity(
            final UUID positionId,
            final UUID rideId,
            final Double latitude,
            final Double longitude,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
            ){
        this.positionId = positionId;
        this.rideId = rideId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PositionEntity() {

    }

    public static PositionEntity fromAggregate(final Position position) {
        return new PositionEntity(
                position.getPositionId(),
                position.getRideId(),
                position.getCoord().getLatitude(),
                position.getCoord().getLongitude(),
                position.getCreatedAt(),
                position.getUpdatedAt()
        );
    }

    public static List<Position> toAggregateList(final List<PositionEntity> lsAllPosition) {
        return lsAllPosition.stream().map(PositionEntity::toAggregate).toList();
    }

    public static Position toAggregate(final PositionEntity positionEntity) {
        return Position.restore(
                positionEntity.positionId,
                positionEntity.rideId,
                positionEntity.latitude,
                positionEntity.longitude,
                positionEntity.createdAt,
                positionEntity.updatedAt
        );
    }
}
