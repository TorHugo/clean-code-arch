package com.dev.torhugo.clean.code.arch.infrastructure.gateway;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.models.AccountDTO;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.gateway.client.ClientConfigHttp;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class AccountGatewayImpl implements AccountGateway {

    private final ClientConfigHttp clientConfigHttp;

    public AccountGatewayImpl(final ClientConfigHttp clientConfigHttp) {
        this.clientConfigHttp = clientConfigHttp;
    }

    @Override
    public AccountDTO getByAccountId(final UUID accountId) {
        final var accountResponse = clientConfigHttp.getAccountByAccountId(accountId);
        if (Objects.isNull(accountResponse))
            throw new GatewayNotFoundError("Account not found! AccountId: " + accountId);
        return AccountDTO.with(
                accountResponse.accountId(),
                accountResponse.name(),
                accountResponse.email(),
                accountResponse.cpf(),
                accountResponse.isPassenger(),
                accountResponse.isDriver(),
                accountResponse.carPlate(),
                accountResponse.createdAt(),
                accountResponse.updateAt()
        );
    }
}
