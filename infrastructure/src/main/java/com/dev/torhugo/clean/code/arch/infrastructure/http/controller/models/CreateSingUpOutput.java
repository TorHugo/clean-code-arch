package com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSingUpOutput(
        @JsonProperty("account_id")
        String accountId
) {
        public static CreateSingUpOutput from(final String accountId){
                return new CreateSingUpOutput(accountId);
        }
}
