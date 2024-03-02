package com.dev.torhugo.clean.code.arch.infrastructure.gateway;

import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.dto.AccountDTO;
import com.dev.torhugo.clean.code.arch.infrastructure.gateway.client.ClientConfigHttp;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountGatewayHttp implements AccountGateway {

    private final ClientConfigHttp clientConfigHttp;

    public AccountGatewayHttp(final ClientConfigHttp clientConfigHttp) {
        this.clientConfigHttp = clientConfigHttp;
    }

    @Override
    public AccountDTO getByAccountId(final UUID accountId) {
        final var accountResponse = clientConfigHttp.getAccountByAccountId(accountId);
        return AccountDTO.create(
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
