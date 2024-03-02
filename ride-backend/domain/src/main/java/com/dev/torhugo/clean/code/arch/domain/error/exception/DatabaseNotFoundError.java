package com.dev.torhugo.clean.code.arch.domain.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.DefaultError;

public class DatabaseNotFoundError extends DefaultError {
    public DatabaseNotFoundError(final String message) {
        super(message);
    }
}
