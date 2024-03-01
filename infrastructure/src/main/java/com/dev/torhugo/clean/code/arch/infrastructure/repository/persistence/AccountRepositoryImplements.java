package com.dev.torhugo.clean.code.arch.infrastructure.repository.persistence;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import com.dev.torhugo.clean.code.arch.infrastructure.database.QueryService;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@PropertySource("classpath:query/account_script.properties")
public class AccountRepositoryImplements implements AccountRepository {

    private final QueryService databaseService;
    public AccountRepositoryImplements(final QueryService databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPS.ACCOUNT_TB.WHERE.EMAIL}")
    private String queryFindAccountByEmail;
    @Value("${SPS.ACCOUNT_TB.WHERE.ACCOUNT_ID}")
    private String queryFindAccountByAccountId;
    @Value("${SPI.ACCOUNT_TB}")
    private String querySaveToNewAccount;

    @Override
    public AccountEntity getByEmail(final String email) {
        return databaseService.retrieve(queryFindAccountByEmail,
                buildParams(email),
                BeanPropertyRowMapper.newInstance(AccountEntity.class))
                .orElse(null);
    }

    @Override
    public void save(final AccountEntity account) {
        databaseService.persist(querySaveToNewAccount, account);
    }

    @Override
    public AccountEntity getByAccountId(final UUID accountId) {
        return databaseService.retrieve(queryFindAccountByAccountId,
                buildParams(accountId),
                BeanPropertyRowMapper.newInstance(AccountEntity.class))
                .orElse(null);
    }

    private MapSqlParameterSource buildParams(final UUID accountId) {
        return new MapSqlParameterSource("accountId", accountId);
    }

    private MapSqlParameterSource buildParams(final String email) {
        return new MapSqlParameterSource("email", email);
    }
}
