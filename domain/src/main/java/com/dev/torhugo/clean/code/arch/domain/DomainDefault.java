package com.dev.torhugo.clean.code.arch.domain;

import java.time.LocalDateTime;

public abstract class DomainDefault extends Validator {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected DomainDefault() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
