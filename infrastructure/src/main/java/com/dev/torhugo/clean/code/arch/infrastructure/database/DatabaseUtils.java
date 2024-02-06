package com.dev.torhugo.clean.code.arch.infrastructure.database;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Optional;

public interface DatabaseUtils {
    <T> Optional<T> retrieve(final String sqlQuery, final SqlParameterSource params, final RowMapper<T> rowMapper);
    void persist(final String query, final Object object);
}
