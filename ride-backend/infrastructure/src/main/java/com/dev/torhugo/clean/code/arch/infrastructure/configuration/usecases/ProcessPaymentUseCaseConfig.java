package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.processpayment.ProcessPaymentUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessPaymentUseCaseConfig {
    private final RideRepository rideRepository;
    public ProcessPaymentUseCaseConfig(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(){
        return new ProcessPaymentUseCase(rideRepository);
    }
}
