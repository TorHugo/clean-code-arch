package com.dev.torhugo.clean.code.arch.infrastructure.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseQueryUtils implements DatabaseUtils{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public <T> Optional<T> retrieve(final String sqlQuery, final SqlParameterSource params, final RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sqlQuery, params, rowMapper));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void persist(final String query, final Object object) {
        final var params = new BeanPropertySqlParameterSource(object);
        namedParameterJdbcTemplate.update(query, params);
    }
}
