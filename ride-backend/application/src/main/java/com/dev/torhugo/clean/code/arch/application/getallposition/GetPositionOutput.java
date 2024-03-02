package com.dev.torhugo.clean.code.arch.application.getallposition;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;

import java.time.LocalDateTime;

public record GetPositionOutput(
        CoordinatesRequestInfo coordinates,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetPositionOutput with(final Position position){
        return new GetPositionOutput(new CoordinatesRequestInfo(position.getCoord().getLatitude(), position.getCoord().getLongitude()), position.getCreatedAt(), position.getUpdatedAt());
    }
}
