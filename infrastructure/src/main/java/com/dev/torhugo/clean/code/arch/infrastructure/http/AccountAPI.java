package com.dev.torhugo.clean.code.arch.infrastructure.http;

import com.dev.torhugo.clean.code.arch.infrastructure.account.models.CreateSingUpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "api/accounts")
public interface AccountAPI {

    @PostMapping(
            "/signup"
    )
    ResponseEntity<?> create(final @RequestBody CreateSingUpRequest input);
}
