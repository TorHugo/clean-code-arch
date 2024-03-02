package com.dev.torhugo.clean.code.arch.infrastructure.gateway.client;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.gateway.models.AccountResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class ClientConfigHttp {

    @Value("${integration.client.account.host}")
    private String host;

    @Value("${integration.client.account.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public ClientConfigHttp(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountResponse getAccountByAccountId(final UUID accountId) {
        final var url = buildUri(accountId);
        try {
            final var response = restTemplate.getForEntity(url, AccountResponse.class);
            return response.getBody();
        } catch (final HttpClientErrorException e){
            throw new GatewayNotFoundError(e.getResponseBodyAsString());
        }
    }

    private String buildUri(final UUID accountId) {
        return UriComponentsBuilder
                .fromUriString(host)
                .path(endpoint)
                .buildAndExpand(accountId).toString();
    }
}
