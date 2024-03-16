package com.dev.torhugo.clean.code.arch.domain.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.DefaultError;

public class ResourceAccessError extends DefaultError {
    public ResourceAccessError(final String message) {
        super(message);
    }
}
