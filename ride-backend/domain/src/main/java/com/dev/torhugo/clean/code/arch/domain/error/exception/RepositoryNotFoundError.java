package com.dev.torhugo.clean.code.arch.domain.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.DefaultError;

public class RepositoryNotFoundError extends DefaultError {
    public RepositoryNotFoundError(final String message) {
        super(message);
    }
}
