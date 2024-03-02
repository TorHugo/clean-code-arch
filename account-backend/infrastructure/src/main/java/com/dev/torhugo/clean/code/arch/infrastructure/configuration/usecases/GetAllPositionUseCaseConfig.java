package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllPositionUseCaseConfig {
    private final PositionGateway positionGateway;

    public GetAllPositionUseCaseConfig(final PositionGateway positionGateway) {
        this.positionGateway = positionGateway;
    }

    @Bean
    public GetAllPositionUseCase getAllPositionUseCase(){
        return new GetAllPositionUseCase(positionGateway);
    }
}
