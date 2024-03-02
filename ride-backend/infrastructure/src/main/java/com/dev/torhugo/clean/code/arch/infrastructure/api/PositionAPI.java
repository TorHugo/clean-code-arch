package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.UpdatePositionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/positions")
public interface PositionAPI {

    @PostMapping(
            "/update"
    )
    ResponseEntity<?> updatePosition(final @RequestBody UpdatePositionRequest request);

    @GetMapping(
            "/all/{rideId}"
    )
    ResponseEntity<?> getAllPositions(final @PathVariable(name = "rideId") UUID rideId);
}
