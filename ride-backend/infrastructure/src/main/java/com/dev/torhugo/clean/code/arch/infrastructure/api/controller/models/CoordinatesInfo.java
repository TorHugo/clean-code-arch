package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CoordinatesInfo(
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude
) {
    public static CoordinatesInfo from(final CoordinatesRequestInfo from) {
        return new CoordinatesInfo(
                from.latitude(),
                from.longitude()
        );
    }
}
