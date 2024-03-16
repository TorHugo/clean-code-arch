package com.dev.torhugo.clean.code.arch.infrastructure.configuration.usecases;

import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePositionUseCaseConfig {
    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;

    public UpdatePositionUseCaseConfig(final RideRepository rideRepository,
                                       final PositionRepository positionRepository) {
        this.rideRepository = rideRepository;
        this.positionRepository = positionRepository;
    }

    @Bean
    public UpdatePositionUseCase updatePositionUseCase(){
        return new UpdatePositionUseCase(rideRepository, positionRepository);
    }
}
