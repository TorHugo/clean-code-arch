package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PositionJpaRepository extends JpaRepository<PositionEntity, UUID> {
    List<PositionEntity> findAllByRideId(final UUID rideId);
}
