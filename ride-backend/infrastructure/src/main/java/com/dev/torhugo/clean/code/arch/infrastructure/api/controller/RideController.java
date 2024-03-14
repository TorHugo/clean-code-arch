package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideInput;
import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.RideAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.AcceptRideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetRideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class RideController implements RideAPI {

    private final RequestRideUseCase requestRideUseCase;
    private final GetRideUseCase getRideUseCase;
    private final AcceptRideUseCase acceptRideUseCase;
    private final StartRideUseCase startRideUseCase;
    private final FinishRideUseCase finishRideUseCase;

    public RideController(final RequestRideUseCase requestRideUseCase,
                          final GetRideUseCase getRideUseCase,
                          final AcceptRideUseCase acceptRideUseCase,
                          final StartRideUseCase startRideUseCase,
                          final FinishRideUseCase finishRideUseCase) {
        this.requestRideUseCase = Objects.requireNonNull(requestRideUseCase);
        this.getRideUseCase = Objects.requireNonNull(getRideUseCase);
        this.acceptRideUseCase = Objects.requireNonNull(acceptRideUseCase);
        this.startRideUseCase = Objects.requireNonNull(startRideUseCase);
        this.finishRideUseCase = Objects.requireNonNull(finishRideUseCase);
    }

    @Override
    public RideResponse create(final RideRequest input) {
        final var requestRideInput = RequestRideInput.with(input.passengerId(), input.from().latitude(), input.from().longitude(), input.to().latitude(), input.to().longitude());
        final var requestRideOutput = this.requestRideUseCase.execute(requestRideInput);
        return RideResponse.from(requestRideOutput);
    }

    @Override
    public GetRideResponse getByRideId(final UUID rideId) {
        final var output = this.getRideUseCase.execute(rideId);
        return GetRideResponse.from(output);
    }

    @Override
    public RideResponse accept(final AcceptRideRequest input) {
        final var acceptInput = AcceptRideInput.with(input.rideId(), input.driverId());
        this.acceptRideUseCase.execute(acceptInput);
        return RideResponse.from(input.rideId().toString());
    }

    @Override
    public RideResponse start(final UUID rideId) {
        this.startRideUseCase.execute(rideId);
        return RideResponse.from(rideId.toString());
    }

    @Override
    public RideResponse finish(final UUID rideId) {
        this.finishRideUseCase.execute(rideId);
        return RideResponse.from(rideId.toString());
    }
}