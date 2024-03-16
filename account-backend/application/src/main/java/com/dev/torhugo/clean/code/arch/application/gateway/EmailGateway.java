package com.dev.torhugo.clean.code.arch.application.gateway;

public interface EmailGateway {
    void sendWelcomeEmail(String email, String message);
}