package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartRideUseCaseConfig {
    private final RideGateway rideGateway;

    public StartRideUseCaseConfig(final RideGateway rideGateway) {
        this.rideGateway = rideGateway;
    }

    @Bean
    public StartRideUseCase startRideUseCase(){
        return new StartRideUseCase(rideGateway);
    }
}
