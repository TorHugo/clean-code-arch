package com.dev.torhugo.clean.code.arch.infrastructure.gateway;

import com.dev.torhugo.clean.code.arch.application.gateway.EmailGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailGatewayImpl implements EmailGateway {
    @Override
    public void sendWelcomeEmail(final String email, final String message) {
        log.info("[WELCOME] - Implemented for welcome email for new account.");
    }
}
