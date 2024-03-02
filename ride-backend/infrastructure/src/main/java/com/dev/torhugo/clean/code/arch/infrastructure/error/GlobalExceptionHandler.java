package com.dev.torhugo.clean.code.arch.infrastructure.error;

import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.DatabaseNotFoundErrorResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.error.exception.InvalidArgumentErrorResponse;
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

    @ExceptionHandler(DatabaseNotFoundError.class)
    public ResponseEntity<Object> handleInvalidArgumentError(final DatabaseNotFoundError ex, final HttpServletRequest request) {
        final var error = DatabaseNotFoundErrorResponse.fromException(ex, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
