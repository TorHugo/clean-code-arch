package com.dev.torhugo.clean.code.arch.infrastructure.messaging.adapter;

import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SingUpInput;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpRequest;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class RabbitMqConsumer {
    private final SignUpUseCase signUpUseCase;
    public RabbitMqConsumer(final SignUpUseCase signUpUseCase) {
        this.signUpUseCase = Objects.requireNonNull(signUpUseCase);
    }
    @RabbitListener(queues = "QUEUE_SIGNUP_ASYNC")
    public void execute(final SingUpRequest input,
                        final Channel channel,
                        final Message message) throws IOException {
        log.info("[-] Process queue message. Input: {}", input);
        final var singUpInput = SingUpInput.with(input.name(), input.email(), input.cpf(), input.carPlate(), input.isPassenger(), input.isDriver());

        try {
            log.info("[-] Process queue message. Input: {}", input);
            signUpUseCase.execute(singUpInput);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (final InvalidArgumentError error) {
            log.error("[InvalidArgumentError] Error: {}", error.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
