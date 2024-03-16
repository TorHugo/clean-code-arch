package com.dev.torhugo.clean.code.arch.infrastructure.messaging.adapter;

import com.dev.torhugo.clean.code.arch.application.processpayment.ProcessPaymentUseCase;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.messaging.models.ProcessPaymentInput;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class ProcessPaymentConsumerAdapter {
    private final ProcessPaymentUseCase useCase;
    public ProcessPaymentConsumerAdapter(final ProcessPaymentUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase);
    }
    @RabbitListener(queues = "QUEUE_PROCESS_PAYMENT")
    public void execute(final ProcessPaymentInput input,
                        final Channel channel,
                        final Message message) throws IOException {
        final var rideId = input.rideId();
        try {
            log.info("[-] Process queue message. Input: {}", input);
            useCase.execute(rideId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (final InvalidArgumentError error) {
            log.error("[InvalidArgumentError] Error: {}", error.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
