package com.dev.torhugo.clean.code.arch.domain.utils;

public enum RideStatusEnumUtils {
    REQUESTED("REQUESTED"),
    ACCEPTED("ACCEPTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),;

    private final String description;

    RideStatusEnumUtils(final String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}
