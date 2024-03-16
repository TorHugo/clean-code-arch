package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinishRideUseCaseConfig {
    private final RideRepository rideRepository;
    private final QueueProducer queueProducer;

    public FinishRideUseCaseConfig(final RideRepository rideRepository,
                                   final QueueProducer queueProducer) {
        this.rideRepository = rideRepository;
        this.queueProducer = queueProducer;
    }

    @Bean
    public FinishRideUseCase finishRideUseCase(){
        return new FinishRideUseCase(rideRepository, queueProducer);
    }
}
