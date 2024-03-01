package com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetPositionOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record GetPositionResponse(
        @JsonProperty("coordinates") CoordinatesInfo coordinates,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        LocalDateTime createdAt,
        @JsonProperty("update_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime updateAt
) {
    public static GetPositionResponse with(final GetPositionOutput position){
        return new GetPositionResponse(
                CoordinatesInfo.from(position.coordinates()),
                position.createdAt(),
                position.updatedAt()
        );
    }
}
