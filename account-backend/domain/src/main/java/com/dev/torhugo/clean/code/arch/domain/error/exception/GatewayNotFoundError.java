package com.dev.torhugo.clean.code.arch.domain.error.exception;

import com.dev.torhugo.clean.code.arch.domain.error.DefaultError;

public class GatewayNotFoundError extends DefaultError {
    public GatewayNotFoundError(final String message) {
        super(message);
    }
}
