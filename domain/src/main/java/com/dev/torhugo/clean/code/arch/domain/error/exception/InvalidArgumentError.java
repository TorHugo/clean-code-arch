package com.dev.torhugo.clean.code.arch.domain.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.DefaultError;

public class InvalidArgumentError extends DefaultError {
    public InvalidArgumentError(final String message) {
        super(message);
    }
}
