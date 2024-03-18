package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {
    private final PositionJpaRepository repository;
    @Override
    public void save(final Position position) {
        final var positionEntity = PositionEntity.fromAggregate(position);
        this.repository.save(positionEntity);
    }

    @Override
    public List<Position> retrieveByRideId(final UUID rideId) {
        return PositionEntity.toAggregateList(this.repository.findAllByRideId(rideId));
    }
}
