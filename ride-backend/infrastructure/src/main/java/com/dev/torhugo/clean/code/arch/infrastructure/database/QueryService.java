package com.dev.torhugo.clean.code.arch.infrastructure.database;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Optional;

public interface QueryService {
    <T> Optional<T> retrieve(final String sqlQuery, final SqlParameterSource params, final RowMapper<T> rowMapper);
    <T> List<T> retrieveList(final String query, final SqlParameterSource params, final RowMapper<T> rowMapper);
    void persist(final String query, final Object object);
}
