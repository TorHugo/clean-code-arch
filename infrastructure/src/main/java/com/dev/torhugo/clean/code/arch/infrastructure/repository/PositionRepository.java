package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.infrastructure.database.QueryService;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.PositionEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/position_script.properties")
public class PositionRepository implements PositionGateway {
    private final QueryService queryService;
    public PositionRepository(final QueryService queryService) {
        this.queryService = queryService;
    }

    @Value("${SPI.POSITION}")
    private String querySavePosition;
    @Value("${SPS.POSITION.WHERE.RIDE_ID}")
    private String queryRetrieveAllPositionByRideId;

    @Override
    public void save(final Position position) {
        final var positionEntity = PositionEntity.fromAggregate(position);
        this.queryService.persist(querySavePosition, positionEntity);
    }

    @Override
    public List<Position> retrieveByRideId(final UUID rideId) {
        final var positions = queryService.retrieveList(queryRetrieveAllPositionByRideId,
                buildParameters(rideId),
                BeanPropertyRowMapper.newInstance(PositionEntity.class));
        return PositionEntity.toAggregateList(positions);
    }

    private MapSqlParameterSource buildParameters(final UUID rideId) {
        return new MapSqlParameterSource("rideId", rideId);
    }
}
