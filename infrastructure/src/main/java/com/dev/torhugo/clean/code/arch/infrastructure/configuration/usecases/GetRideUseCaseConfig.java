package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetRideUseCaseConfig {
    private final RideGateway rideGateway;
    private final AccountGateway accountGateway;

    public GetRideUseCaseConfig(final RideGateway rideGateway,
                                final AccountGateway accountGateway) {
        this.rideGateway = rideGateway;
        this.accountGateway = accountGateway;
    }
    @Bean
    public GetRideUseCase getRideUseCase(){
        return new GetRideUseCase(rideGateway, accountGateway);
    }
}
