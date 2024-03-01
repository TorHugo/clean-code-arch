package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionOutput;
import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionInput;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.PositionAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAccountResponse;
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
    public ResponseEntity<?> updatePosition(final UpdatePositionRequest request) {
        final var updatePositionInput = UpdatePositionInput.with(request.rideId(), request.latitude(), request.latitude());
        this.updatePositionUseCase.execute(updatePositionInput);
        return ResponseEntity.status(HttpStatus.OK).body(RideResponse.from(request.rideId().toString()));
    }

    @Override
    public ResponseEntity<?> getAllPositions(final UUID rideId) {
        final var allPositions = getAllPositionUseCase.execute(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(GetAllPositionResponse.from(rideId, allPositions));
    }
}
