package com.dev.torhugo.clean.code.arch.infrastructure.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.DefaultError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class DatabaseNotFoundErrorResponse extends DefaultError {
    protected DatabaseNotFoundErrorResponse(final Instant timestamp,
                                            final Integer status,
                                            final String error,
                                            final String message,
                                            final String path) {
        super(timestamp, status, error, message, path);
    }

    public static DatabaseNotFoundErrorResponse fromException(final DatabaseNotFoundError ex, final HttpServletRequest request){
        return new DatabaseNotFoundErrorResponse(
                Instant.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
