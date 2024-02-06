package com.dev.torhugo.clean.code.arch.infrastructure.account.persistence;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.infrastructure.database.DatabaseQueryUtils;
import com.dev.torhugo.clean.code.arch.infrastructure.database.DatabaseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@PropertySource("classpath:query/account_script.properties")
public class AccountRepositoryImplements implements AccountRepository{

    private final DatabaseUtils databaseService;
    public AccountRepositoryImplements(final DatabaseUtils databaseService) {
        this.databaseService = databaseService;
    }

    @Value("${SPS.ACCOUNT_TB.WHERE.EMAIL}")
    private String queryFindAccountByEmail;
    @Value("${SPI.ACCOUNT_TB}")
    private String querySaveToNewAccount;

    @Override
    public Optional<Account> getByEmail(final String email) {
        return databaseService.retrieve(queryFindAccountByEmail,
                buildParams(email),
                BeanPropertyRowMapper.newInstance(Account.class));
    }

    @Override
    public void save(final Account account) {
        databaseService.persist(querySaveToNewAccount, account);
    }

    private MapSqlParameterSource buildParams(final String email) {
        return new MapSqlParameterSource("email", email);
    }
}
