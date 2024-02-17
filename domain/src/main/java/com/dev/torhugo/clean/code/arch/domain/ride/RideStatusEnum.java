package com.dev.torhugo.clean.code.arch.domain.ride;

public enum RideStatusEnum {
    REQUESTED("REQUESTED"),
    ACCEPTED("ACCEPTED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String description;

    RideStatusEnum(final String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}
