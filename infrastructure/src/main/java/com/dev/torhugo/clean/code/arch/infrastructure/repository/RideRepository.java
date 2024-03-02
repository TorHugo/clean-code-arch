package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.database.QueryService;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.RideEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/ride_scripts.properties")
public class RideRepository implements RideGateway {

    private final QueryService databaseService;

    public RideRepository(final QueryService databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPI.RIDE}")
    private String querySaveToNewRide;
    @Value("${SPU.RIDE}")
    private String queryUpdateToExistingRide;
    @Value("${SPS.RIDE.WHERE.PASSENGER_ID.AND.STATUS}")
    private String queryFindRideByPassengerIdAndStatus;
    @Value("${SPS.RIDE.WHERE.DRIVER_ID.AND.STATUS}")
    private String queryFindRideByDriverIdAndStatus;
    @Value("${SPS.RIDE.WHERE.RIDE_ID}")
    private String queryFindRideById;

    @Override
    public void save(final Ride ride) {
        final var rideEntity = RideEntity.fromAggregate(ride);
        databaseService.persist(querySaveToNewRide, rideEntity);
    }

    @Override
    public List<Ride> getAllRidesWithStatus(final UUID accountId,
                                                  final boolean isPassenger,
                                                  final String status) {
        if (isPassenger) {
            final var lsRidesPassenger = databaseService.retrieveList(queryFindRideByPassengerIdAndStatus,
                    buildParametersWithStatus(accountId, status),
                    BeanPropertyRowMapper.newInstance(RideEntity.class));
            return RideEntity.toAggregateList(lsRidesPassenger);
        }

        final var lsRidesDriver = databaseService.retrieveList(queryFindRideByDriverIdAndStatus,
                buildParametersWithStatus(accountId, status),
                BeanPropertyRowMapper.newInstance(RideEntity.class));
        return RideEntity.toAggregateList(lsRidesDriver);
    }

    @Override
    public Ride getRideById(final UUID rideId) {
        final var rideEntity = databaseService.retrieve(queryFindRideById,
                        buildParametersRideId(rideId),
                        BeanPropertyRowMapper.newInstance(RideEntity.class))
                .orElse(null);
        return Objects.isNull(rideEntity) ? null : RideEntity.toAggregate(rideEntity);
    }

    @Override
    public void update(final Ride ride) {
        final var rideEntity = RideEntity.fromAggregate(ride);
        databaseService.persist(queryUpdateToExistingRide, rideEntity);
    }

    private MapSqlParameterSource buildParametersRideId(final UUID rideId) {
        return new MapSqlParameterSource("rideId", rideId);
    }

    private MapSqlParameterSource buildParametersWithStatus(final UUID accountId, final String status) {
        final var parameter = new MapSqlParameterSource();
        parameter.addValue("account", accountId);
        parameter.addValue("status", status);
        return parameter;
    }
}
