package com.dev.torhugo.clean.code.arch.infrastructure.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.DefaultError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidArgumentErrorResponse extends DefaultError {
    protected InvalidArgumentErrorResponse(final Instant timestamp,
                                           final Integer status,
                                           final String error,
                                           final String message,
                                           final String path) {
        super(timestamp, status, error, message, path);
    }

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
