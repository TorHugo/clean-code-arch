package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinishRideUseCaseConfig {
    private final RideGateway rideGateway;

    public FinishRideUseCaseConfig(final RideGateway rideGateway) {
        this.rideGateway = rideGateway;
    }

    @Bean
    public FinishRideUseCase finishRideUseCase(){
        return new FinishRideUseCase(rideGateway);
    }
}
