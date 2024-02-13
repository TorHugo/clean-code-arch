package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/rides")
public interface RideAPI {
    @PostMapping(
            "/requested"
    )
    ResponseEntity<?> create(final @RequestBody RideRequest input);

}
