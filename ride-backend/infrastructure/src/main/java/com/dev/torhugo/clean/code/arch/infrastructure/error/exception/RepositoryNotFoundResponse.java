package com.dev.torhugo.clean.code.arch.infrastructure.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.DefaultError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

public class RepositoryNotFoundResponse extends DefaultError {
    protected RepositoryNotFoundResponse(final Instant timestamp,
                                         final Integer status,
                                         final String error,
                                         final String message,
                                         final String path) {
        super(timestamp, status, error, message, path);
    }

    public static RepositoryNotFoundResponse fromException(final RepositoryNotFoundError err,
                                                           final HttpServletRequest request){
        return new RepositoryNotFoundResponse(
                Instant.now(),
                BAD_GATEWAY.value(),
                BAD_GATEWAY.getReasonPhrase(),
                err.getMessage(),
                request.getRequestURI()
        );
    }
}
