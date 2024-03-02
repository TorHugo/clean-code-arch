package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcceptRideUseCaseConfig {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;

    public AcceptRideUseCaseConfig(final RideGateway rideGateway,
                                   final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }
    @Bean
    public AcceptRideUseCase acceptRideUseCase(){
        return new AcceptRideUseCase(rideGateway, accountGateway);
    }
}
