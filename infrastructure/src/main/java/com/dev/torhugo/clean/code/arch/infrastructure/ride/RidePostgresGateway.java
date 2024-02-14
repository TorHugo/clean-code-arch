package com.dev.torhugo.clean.code.arch.infrastructure.ride;

import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;
import com.dev.torhugo.clean.code.arch.infrastructure.ride.models.RideEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.ride.persistence.RideRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class RidePostgresGateway implements RideGateway {

    private final RideRepository rideRepository;

    public RidePostgresGateway(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public List<Ride> getActiveRidesByPassengerId(final UUID accountId) {
        final var activesRides = this.rideRepository.getActiveRidesByPassengerId(accountId);
        return RideEntity.toAggregateList(activesRides);
    }

    @Override
    public void save(final Ride ride) {
        final var rideEntity = RideEntity.fromAggregate(ride);
        this.rideRepository.save(rideEntity);
    }

    @Override
    public Ride getRideById(final UUID rideId) {
        final var ride = this.rideRepository.getRideById(rideId);
        return Objects.isNull(ride) ? null : RideEntity.toAggregate(ride);
    }
}
