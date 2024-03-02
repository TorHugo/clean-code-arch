package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SingUpResponse(
        @JsonProperty("account_id")
        String accountId
) {
        public static SingUpResponse from(final String accountId){
                return new SingUpResponse(accountId);
        }
}
