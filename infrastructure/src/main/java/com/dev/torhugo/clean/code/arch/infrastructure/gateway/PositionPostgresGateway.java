package com.dev.torhugo.clean.code.arch.infrastructure.gateway;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PositionPostgresGateway implements PositionGateway {

    private final PositionRepository positionRepository;

    public PositionPostgresGateway(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public void save(final Position position) {
        final var positionEntity = PositionEntity.fromAggregate(position);
        this.positionRepository.save(positionEntity);
    }

    @Override
    public List<Position> retrieveByRideId(final UUID rideId) {
        final var lsAllPosition = positionRepository.retrieveAllPositionByRideId(rideId);
        return PositionEntity.toAggregateList(lsAllPosition);
    }
}
