package com.dev.torhugo.clean.code.arch.application.getallposition;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;

import java.util.List;
import java.util.UUID;

public record GetAllPositionOutput(
        UUID rideId,
        List<GetPositionOutput> lsPositions
) {
    public static GetAllPositionOutput with(final UUID rideId, final List<Position> lsPositions) {
        final var lsPositionsOutput = lsPositions.stream().map(GetPositionOutput::with).toList();
        return new GetAllPositionOutput(rideId, lsPositionsOutput);
    }
}
