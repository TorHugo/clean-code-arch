package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestRideUseCaseConfig {
    private final RideRepository rideRepository;
    private final AccountGateway accountGateway;

    public RequestRideUseCaseConfig(final RideRepository rideRepository,
                                    final AccountGateway accountGateway) {
        this.rideRepository = rideRepository;
        this.accountGateway = accountGateway;
    }
    @Bean
    public RequestRideUseCase requestRideUseCase(){
        return new RequestRideUseCase(accountGateway, rideRepository);
    }

}
