package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.RideEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RideRepositoryImpl implements RideRepository {
    private final RideJpaRepository rideJpaRepository;
    @Override
    public void save(final Ride ride) {
        log.info("save()");
        final var rideEntity = RideEntity.fromAggregate(ride);
        this.rideJpaRepository.save(rideEntity);
    }

    @Override
    public List<Ride> getAllRidesWithStatus(final UUID accountId,
                                            final boolean isPassenger,
                                            final String status) {
        if (isPassenger) {
            final var result = this.rideJpaRepository.findRidesByStatusAndPassengerId(status, accountId);
            return RideEntity.toAggregateList(result);
        }

        final var result = this.rideJpaRepository.findRidesByStatusAndDriverId(status, accountId);
        return RideEntity.toAggregateList(result);
    }

    @Override
    public Ride getRideById(final UUID rideId) {
        final var rideEntity = this.rideJpaRepository.findById(rideId);
        return rideEntity.map(RideEntity::toAggregate)
                .orElseThrow(() -> new RepositoryNotFoundError("Ride not found!"));
    }
}
