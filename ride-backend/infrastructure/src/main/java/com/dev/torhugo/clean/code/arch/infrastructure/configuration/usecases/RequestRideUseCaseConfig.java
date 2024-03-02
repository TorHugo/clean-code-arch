package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestRideUseCaseConfig {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;

    public RequestRideUseCaseConfig(final RideGateway rideGateway,
                                    final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }
    @Bean
    public RequestRideUseCase requestRideUseCase(){
        return new RequestRideUseCase(accountGateway, rideGateway);
    }

}
