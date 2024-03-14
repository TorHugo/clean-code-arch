package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.AcceptRideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetRideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/rides")
public interface RideAPI {
    @PostMapping(
            "/request"
    )
    @ResponseStatus(HttpStatus.CREATED)
    RideResponse create(final @RequestBody RideRequest input);

    @GetMapping(
            "/{rideId}"
    )
    @ResponseStatus(HttpStatus.OK)
    GetRideResponse getByRideId(final @PathVariable UUID rideId);
    @PutMapping(
            "/accept"
    )
    @ResponseStatus(HttpStatus.OK)
    RideResponse accept(final @RequestBody AcceptRideRequest input);
    @PutMapping(
            "/start/{rideId}"
    )
    @ResponseStatus(HttpStatus.OK)
    RideResponse start(final @PathVariable UUID rideId);

    @PutMapping(
            "/finish/{rideId}"
    )
    @ResponseStatus(HttpStatus.OK)
    RideResponse finish(final @PathVariable UUID rideId);
}
