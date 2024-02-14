package com.dev.torhugo.clean.code.arch.infrastructure.ride.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.account.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.database.DatabaseUtils;
import com.dev.torhugo.clean.code.arch.infrastructure.ride.models.RideEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/ride_scripts.properties")
public class RideRepositoryImplements implements RideRepository{

    private final DatabaseUtils databaseService;

    public RideRepositoryImplements(final DatabaseUtils databaseService) {
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
    public void save(final RideEntity rideEntity) {
        databaseService.persist(querySaveToNewRide, rideEntity);
    }

    @Override
    public List<RideEntity> getAllRidesWithStatus(final AccountEntity account, final String status) {
        if (account.isPassenger())
            return databaseService.retrieveList(queryFindRideByPassengerIdAndStatus,
                    buildParametersWithStatus(account.getAccountId(), status),
                    BeanPropertyRowMapper.newInstance(RideEntity.class));

        return databaseService.retrieveList(queryFindRideByDriverIdAndStatus,
                buildParametersWithStatus(account.getAccountId(), status),
                BeanPropertyRowMapper.newInstance(RideEntity.class));
    }

    @Override
    public RideEntity getRideById(final UUID rideId) {
        return databaseService.retrieve(queryFindRideById,
                buildParametersRideId(rideId),
                BeanPropertyRowMapper.newInstance(RideEntity.class))
                .orElse(null);
    }

    @Override
    public void update(final RideEntity rideEntity) {
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
