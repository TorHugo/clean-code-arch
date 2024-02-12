package com.dev.torhugo.clean.code.arch.infrastructure.http.exception.models;

import com.dev.torhugo.clean.code.arch.domain.error.InvalidArgumentError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public record InvalidArgumentErrorResponse(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
    public static InvalidArgumentErrorResponse fromException(final InvalidArgumentError ex, final HttpServletRequest request){
        return new InvalidArgumentErrorResponse(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
