package com.dev.torhugo.clean.code.arch.infrastructure.api;

import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetAccountResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpRequest;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/accounts")
public interface AccountAPI {

    @PostMapping(
            "/signup"
    )
    @ResponseStatus(HttpStatus.CREATED)
    SingUpResponse create(final @RequestBody SingUpRequest input);

    @PostMapping(
            "/signup-async"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void signupAsync(final @RequestBody SingUpRequest input);

    @GetMapping(
            value = "get-account/{accountId}"
    )
    @ResponseStatus(HttpStatus.OK)
    GetAccountResponse getAccount(final @PathVariable(name = "accountId") UUID accountId);
}
