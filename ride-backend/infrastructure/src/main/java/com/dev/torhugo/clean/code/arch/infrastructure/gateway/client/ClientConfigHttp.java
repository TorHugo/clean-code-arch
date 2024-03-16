package com.dev.torhugo.clean.code.arch.infrastructure.gateway.client;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.ResourceAccessError;
import com.dev.torhugo.clean.code.arch.infrastructure.gateway.models.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@Slf4j
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
            log.error("[HttpClientErrorException] Error: {}", e.getMessage());
            throw new GatewayNotFoundError(e.getResponseBodyAsString());
        } catch (final ResourceAccessException e){
            log.error("[ResourceAccessError] Error: {}", e.getMessage());
            throw new ResourceAccessError(e.getCause().getMessage());
        } catch (final Exception e){
            log.error("[Exception] Error: {}", e.getMessage());
            throw new ResourceAccessError(e.getCause().getMessage());
        }
    }

    private String buildUri(final UUID accountId) {
        return UriComponentsBuilder
                .fromUriString(host)
                .path(endpoint)
                .buildAndExpand(accountId).toString();
    }
}
