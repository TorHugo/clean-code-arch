package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetRideUseCaseConfig {
    private final RideRepository rideRepository;
    private final AccountGateway accountGateway;

    public GetRideUseCaseConfig(final RideRepository rideRepository,
                                final AccountGateway accountGateway) {
        this.rideRepository = rideRepository;
        this.accountGateway = accountGateway;
    }
    @Bean
    public GetRideUseCase getRideUseCase(){
        return new GetRideUseCase(rideRepository, accountGateway);
    }
}
