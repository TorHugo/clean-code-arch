package com.dev.torhugo.clean.code.arch.infrastructure.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.ResourceAccessError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.DefaultError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

public class ResourceAccessErrorResponse extends DefaultError {
    protected ResourceAccessErrorResponse(final Instant timestamp,
                                          final Integer status,
                                          final String error,
                                          final String message,
                                          final String path) {
        super(timestamp, status, error, message, path);
    }

    public static ResourceAccessErrorResponse fromException(final String message,
                                                            final HttpServletRequest request){
        return new ResourceAccessErrorResponse(
                Instant.now(),
                BAD_GATEWAY.value(),
                BAD_GATEWAY.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }
}
