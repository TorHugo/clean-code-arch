package com.dev.torhugo.clean.code.arch.infrastructure.http.exception;

import com.dev.torhugo.clean.code.arch.domain.error.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.http.exception.models.InvalidArgumentErrorResponse;
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
}
