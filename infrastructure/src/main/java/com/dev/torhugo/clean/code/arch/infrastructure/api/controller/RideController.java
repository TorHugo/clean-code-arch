package com.dev.torhugo.clean.code.arch.infrastructure.api.controller;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideInput;
import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.RideAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.AcceptRideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetRideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class RideController implements RideAPI {

    private final RequestRideUseCase requestRideUseCase;
    private final GetRideUseCase getRideUseCase;
    private final AcceptRideUseCase acceptRideUseCase;
    private final StartRideUseCase startRideUseCase;

    public RideController(final RequestRideUseCase requestRideUseCase,
                          final GetRideUseCase getRideUseCase,
                          final AcceptRideUseCase acceptRideUseCase,
                          final StartRideUseCase startRideUseCase) {
        this.requestRideUseCase = Objects.requireNonNull(requestRideUseCase);
        this.getRideUseCase = Objects.requireNonNull(getRideUseCase);
        this.acceptRideUseCase = Objects.requireNonNull(acceptRideUseCase);
        this.startRideUseCase = Objects.requireNonNull(startRideUseCase);
    }

    @Override
    public ResponseEntity<?> create(final RideRequest input) {
        final var requestRideInput = RequestRideInput.with(input.passengerId(), input.from().latitude(), input.from().longitude(), input.to().latitude(), input.to().longitude());
        final var requestRideOutput = this.requestRideUseCase.execute(requestRideInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(RideResponse.from(requestRideOutput));
    }

    @Override
    public ResponseEntity<?> getByRideId(final UUID rideId) {
        final var output = this.getRideUseCase.execute(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(GetRideResponse.from(output));
    }

    @Override
    public ResponseEntity<?> accept(final AcceptRideRequest input) {
        final var acceptInput = AcceptRideInput.with(input.rideId(), input.driverId());
        this.acceptRideUseCase.execute(acceptInput);
        return ResponseEntity.status(HttpStatus.OK).body(RideResponse.from(input.rideId().toString()));
    }

    @Override
    public ResponseEntity<?> start(final UUID rideId) {
        this.startRideUseCase.execute(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(RideResponse.from(rideId.toString()));
    }
}