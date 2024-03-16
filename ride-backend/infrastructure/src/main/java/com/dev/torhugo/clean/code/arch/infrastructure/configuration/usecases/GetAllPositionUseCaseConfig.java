package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllPositionUseCaseConfig {
    private final PositionRepository positionRepository;

    public GetAllPositionUseCaseConfig(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Bean
    public GetAllPositionUseCase getAllPositionUseCase(){
        return new GetAllPositionUseCase(positionRepository);
    }
}
