package com.dev.torhugo.clean.code.arch.domain.enums;

public enum MessageEnum {
    WELCOME("Welcome to course clean code and clean architecture!");
    private final String message;
    MessageEnum(final String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
