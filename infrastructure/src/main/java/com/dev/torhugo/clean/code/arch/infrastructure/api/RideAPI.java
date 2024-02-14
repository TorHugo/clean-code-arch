package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/rides")
public interface RideAPI {
    @PostMapping(
            "/requested"
    )
    ResponseEntity<?> create(final @RequestBody RideRequest input);

    @GetMapping(
            "/{rideId}"
    )
    ResponseEntity<?> getByRideId(final @PathVariable UUID rideId);
}
