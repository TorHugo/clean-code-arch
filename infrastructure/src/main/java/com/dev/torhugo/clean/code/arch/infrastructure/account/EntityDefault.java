package com.dev.torhugo.clean.code.arch.infrastructure.account;

import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class EntityDefault {
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    protected EntityDefault() {
        this.createdAt = null;
        this.updatedAt = null;
    }

}
