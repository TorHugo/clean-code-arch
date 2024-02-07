package com.dev.torhugo.clean.code.arch.infrastructure.http;

import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.SingUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/accounts")
public interface AccountAPI {

    @PostMapping(
            "/signup"
    )
    ResponseEntity<?> create(final @RequestBody SingUpRequest input);
}
