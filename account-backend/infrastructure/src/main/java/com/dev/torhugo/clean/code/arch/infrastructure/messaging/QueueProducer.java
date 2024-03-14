package com.dev.torhugo.clean.code.arch.infrastructure.messaging;

public interface QueueProducer {
    void sendMessage(final String queue,
                     final Object message);
}
