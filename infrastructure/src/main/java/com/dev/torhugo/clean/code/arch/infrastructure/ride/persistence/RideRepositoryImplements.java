package com.dev.torhugo.clean.code.arch.infrastructure.ride.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.database.DatabaseUtils;
import com.dev.torhugo.clean.code.arch.infrastructure.ride.models.RideEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.ride.RideStatusEnum.REQUESTED;

@Repository
@PropertySource("classpath:query/ride_scripts.properties")
public class RideRepositoryImplements implements RideRepository{

    private final DatabaseUtils databaseService;

    public RideRepositoryImplements(final DatabaseUtils databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPI.RIDE}")
    private String querySaveToNewRide;
    @Value("${SPS.RIDE.WHERE.ACCOUNT_ID.AND.STATUS}")
    private String queryFindRideByAccountIdAndStatus;

    @Override
    public void save(final RideEntity rideEntity) {
        databaseService.persist(querySaveToNewRide, rideEntity);
    }

    @Override
    public List<RideEntity> getActiveRidesByPassengerId(final UUID accountId) {
        return databaseService.retrieveList(queryFindRideByAccountIdAndStatus,
                buildParameters(accountId),
                BeanPropertyRowMapper.newInstance(RideEntity.class));
    }

    private MapSqlParameterSource buildParameters(final UUID accountId) {
        final var parameter = new MapSqlParameterSource();
        parameter.addValue("passengerId", accountId);
        parameter.addValue("status", REQUESTED.getName());
        return parameter;
    }
}
