package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.database.QueryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/account_script.properties")
public class AccountRepositoryImpl implements AccountRepository {
    private final QueryService databaseService;
    public AccountRepositoryImpl(final QueryService databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPS.ACCOUNT_TB.WHERE.EMAIL}")
    private String queryFindAccountByEmail;
    @Value("${SPS.ACCOUNT_TB.WHERE.ACCOUNT_ID}")
    private String queryFindAccountByAccountId;
    @Value("${SPI.ACCOUNT_TB}")
    private String querySaveToNewAccount;

    @Override
    public Account getByEmail(final String email) {
        final var accountEntity = databaseService.retrieve(queryFindAccountByEmail,
                        buildParams(email),
                        BeanPropertyRowMapper.newInstance(AccountEntity.class))
                .orElse(null);
        return Objects.isNull(accountEntity) ? null : AccountEntity.toAggregate(accountEntity);
    }

    @Override
    public void save(final Account account) {
        final var accountEntity = AccountEntity.fromAggregate(account);
        databaseService.persist(querySaveToNewAccount, accountEntity);
    }

    @Override
    public Account getByAccountId(final UUID accountId) {
        final var accountEntity = databaseService.retrieve(queryFindAccountByAccountId,
                        buildParams(accountId),
                        BeanPropertyRowMapper.newInstance(AccountEntity.class))
                .orElse(null);
        return Objects.isNull(accountEntity) ? null : AccountEntity.toAggregate(accountEntity);
    }

    private MapSqlParameterSource buildParams(final UUID accountId) {
        return new MapSqlParameterSource("accountId", accountId);
    }

    private MapSqlParameterSource buildParams(final String email) {
        return new MapSqlParameterSource("email", email);
    }
}