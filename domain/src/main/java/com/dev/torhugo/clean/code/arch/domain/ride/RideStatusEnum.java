package com.dev.torhugo.clean.code.arch.domain.ride;

public enum RideStatusEnum {
    REQUESTED("requested");

    private final String name;

    RideStatusEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
