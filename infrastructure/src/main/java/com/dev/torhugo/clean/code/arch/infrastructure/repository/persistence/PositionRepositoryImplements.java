package com.dev.torhugo.clean.code.arch.infrastructure.repository.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.database.QueryService;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/position_script.properties")
public class PositionRepositoryImplements implements PositionRepository {
    private final QueryService queryService;
    public PositionRepositoryImplements(final QueryService queryService) {
        this.queryService = queryService;
    }

    @Value("${SPI.POSITION}")
    private String querySavePosition;
    @Value("${SPS.POSITION.WHERE.RIDE_ID}")
    private String queryRetrieveAllPositionByRideId;

    @Override
    public void save(final PositionEntity positionEntity) {
        this.queryService.persist(querySavePosition, positionEntity);
    }

    @Override
    public List<PositionEntity> retrieveAllPositionByRideId(final UUID rideId) {
        return this.queryService.retrieveList(queryRetrieveAllPositionByRideId,
                buildParameters(rideId),
                BeanPropertyRowMapper.newInstance(PositionEntity.class));
    }

    private MapSqlParameterSource buildParameters(final UUID rideId) {
        return new MapSqlParameterSource("rideId", rideId);
    }
}
