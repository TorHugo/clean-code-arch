package com.dev.torhugo.clean.code.arch.infrastructure.account.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.account.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.database.DatabaseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@PropertySource("classpath:query/account_script.properties")
public class AccountRepositoryImplements implements AccountRepository {

    private final DatabaseUtils databaseService;
    public AccountRepositoryImplements(final DatabaseUtils databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPS.ACCOUNT_TB.WHERE.EMAIL}")
    private String queryFindAccountByEmail;
    @Value("${SPS.ACCOUNT_TB.WHERE.ACCOUNT_ID}")
    private String queryFindAccountByAccountId;
    @Value("${SPI.ACCOUNT_TB}")
    private String querySaveToNewAccount;

    @Override
    public Optional<AccountEntity> getByEmail(final String email) {
        return databaseService.retrieve(queryFindAccountByEmail,
                buildParams(email),
                BeanPropertyRowMapper.newInstance(AccountEntity.class));
    }

    @Override
    public void save(final AccountEntity account) {
        databaseService.persist(querySaveToNewAccount, account);
    }

    @Override
    public Optional<AccountEntity> getByAccountId(final UUID accountId) {
        return databaseService.retrieve(queryFindAccountByAccountId,
                buildParams(accountId),
                BeanPropertyRowMapper.newInstance(AccountEntity.class));
    }

    private MapSqlParameterSource buildParams(final UUID accountId) {
        return new MapSqlParameterSource("accountId", accountId);
    }

    private MapSqlParameterSource buildParams(final String email) {
        return new MapSqlParameterSource("email", email);
    }
}
