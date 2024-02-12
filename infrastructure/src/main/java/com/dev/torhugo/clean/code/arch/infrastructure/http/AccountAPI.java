package com.dev.torhugo.clean.code.arch.infrastructure.http;

import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.SingUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/accounts")
public interface AccountAPI {

    @PostMapping(
            "/signup"
    )
    ResponseEntity<?> create(final @RequestBody SingUpRequest input);

    @GetMapping(
            value = "get-account/{accountId}"
    )
    ResponseEntity<?> getAccount(final @PathVariable(name = "accountId") UUID accountId);
}
