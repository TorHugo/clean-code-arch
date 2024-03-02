package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePositionUseCaseConfig {
    private final RideGateway rideGateway;
    private final PositionGateway positionGateway;

    public UpdatePositionUseCaseConfig(final RideGateway rideGateway,
                                       final PositionGateway positionGateway) {
        this.rideGateway = rideGateway;
        this.positionGateway = positionGateway;
    }

    @Bean
    public UpdatePositionUseCase updatePositionUseCase(){
        return new UpdatePositionUseCase(rideGateway, positionGateway);
    }
}
