package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoordinatesRequestInfo(
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude
) {
}
