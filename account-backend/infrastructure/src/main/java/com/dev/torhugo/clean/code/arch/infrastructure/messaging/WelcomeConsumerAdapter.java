package com.dev.torhugo.clean.code.arch.infrastructure.messaging;

import com.dev.torhugo.clean.code.arch.application.gateway.EmailGateway;
import com.dev.torhugo.clean.code.arch.infrastructure.messaging.models.WelcomeInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j // adapters
public class WelcomeConsumerAdapter {
    private final EmailGateway emailGateway;
    public WelcomeConsumerAdapter(final EmailGateway emailGateway) {
        this.emailGateway = Objects.requireNonNull(emailGateway);
    }
//    @RabbitListener(queues = "QUEUE_SIGN_UP_WELCOME")
    public void execute(final WelcomeInput input) {
        log.info("[-] Process queue message. : {}", input);
        this.emailGateway.sendWelcomeEmail(input.email(), input.message());
    }


}
