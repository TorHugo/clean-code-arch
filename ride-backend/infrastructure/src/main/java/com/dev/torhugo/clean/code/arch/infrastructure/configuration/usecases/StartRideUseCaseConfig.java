package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartRideUseCaseConfig {
    private final RideRepository rideRepository;

    public StartRideUseCaseConfig(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Bean
    public StartRideUseCase startRideUseCase(){
        return new StartRideUseCase(rideRepository);
    }
}
