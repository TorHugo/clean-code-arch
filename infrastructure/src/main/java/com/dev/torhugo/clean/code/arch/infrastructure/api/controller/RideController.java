package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.RideAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class RideController implements RideAPI {

    private final RequestRideUseCase requestRideUseCase;

    public RideController(final RequestRideUseCase requestRideUseCase) {
        this.requestRideUseCase = Objects.requireNonNull(requestRideUseCase);
    }

    @Override
    public ResponseEntity<?> create(final RideRequest input) {
        final var requestRideInput = RequestRideInput.with(input.passengerId(), input.from().latitude(), input.from().longitude(), input.to().latitude(), input.to().longitude());
        final var requestRideOutput = this.requestRideUseCase.execute(requestRideInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(RideResponse.from(requestRideOutput));
    }
}
