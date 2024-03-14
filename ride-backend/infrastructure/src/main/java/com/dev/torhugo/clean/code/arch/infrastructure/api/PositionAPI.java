package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAllPositionResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.UpdatePositionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/positions")
public interface PositionAPI {

    @PostMapping(
            "/update"
    )
    @ResponseStatus(HttpStatus.OK)
    RideResponse updatePosition(final @RequestBody UpdatePositionRequest request);

    @GetMapping(
            "/all/{rideId}"
    )
    @ResponseStatus(HttpStatus.OK)
    GetAllPositionResponse getAllPositions(final @PathVariable(name = "rideId") UUID rideId);
}
