package com.dev.torhugo.clean.code.arch.infrastructure.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.DefaultError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class GatewayNotFoundErrorResponse extends DefaultError {
    protected GatewayNotFoundErrorResponse(final Instant timestamp,
                                           final Integer status,
                                           final String error,
                                           final String message,
                                           final String path) {
        super(timestamp, status, error, message, path);
    }

    public static GatewayNotFoundErrorResponse fromException(final GatewayNotFoundError ex, final HttpServletRequest request){
        return new GatewayNotFoundErrorResponse(
                Instant.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
