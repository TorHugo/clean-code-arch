package com.dev.torhugo.clean.code.arch.infrastructure.error;

import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.ResourceAccessError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.GatewayNotFoundErrorResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.InvalidArgumentErrorResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.RepositoryNotFoundResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.ResourceAccessErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgumentError.class)
    public ResponseEntity<Object> handleInvalidArgumentError(final InvalidArgumentError ex, final HttpServletRequest request) {
        final var error = InvalidArgumentErrorResponse.fromException(ex, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(GatewayNotFoundError.class)
    public ResponseEntity<Object> handleInvalidArgumentError(final GatewayNotFoundError ex, final HttpServletRequest request) {
        final var error = GatewayNotFoundErrorResponse.fromException(ex, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(RepositoryNotFoundError.class)
    public ResponseEntity<Object> handleRepositoryNotFoundError(final RepositoryNotFoundError ex, final HttpServletRequest request) {
        final var error = RepositoryNotFoundResponse.fromException(ex, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ResourceAccessError.class)
    public ResponseEntity<Object> handleInvalidArgumentError(final ResourceAccessError ex, final HttpServletRequest request) {
        final var message = ex.getMessage();
        final var error = ResourceAccessErrorResponse.fromException(message, request);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }
}
