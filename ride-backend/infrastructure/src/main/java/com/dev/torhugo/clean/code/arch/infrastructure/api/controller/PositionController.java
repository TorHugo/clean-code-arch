package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionInput;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.PositionAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAllPositionResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.UpdatePositionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class PositionController implements PositionAPI {

    private final UpdatePositionUseCase updatePositionUseCase;
    private final GetAllPositionUseCase getAllPositionUseCase;

    public PositionController(final UpdatePositionUseCase updatePositionUseCase,
                              final GetAllPositionUseCase getAllPositionUseCase) {
        this.updatePositionUseCase = Objects.requireNonNull(updatePositionUseCase);
        this.getAllPositionUseCase = Objects.requireNonNull(getAllPositionUseCase);
    }

    @Override
    public RideResponse updatePosition(final UpdatePositionRequest request) {
        final var updatePositionInput = UpdatePositionInput.with(request.rideId(), request.latitude(), request.longitude());
        this.updatePositionUseCase.execute(updatePositionInput);
        return RideResponse.from(request.rideId().toString());
    }

    @Override
    public GetAllPositionResponse getAllPositions(final UUID rideId) {
        final var allPositions = getAllPositionUseCase.execute(rideId);
        return GetAllPositionResponse.from(rideId, allPositions);
    }
}
