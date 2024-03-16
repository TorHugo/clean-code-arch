package com.dev.torhugo.clean.code.arch.application.finishride;

import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;

import java.util.Objects;
import java.util.UUID;

public class FinishRideUseCase {

    private final RideRepository rideRepository;
    private final QueueProducer queueProducer;

    public FinishRideUseCase(final RideRepository rideRepository,
                             final QueueProducer queueProducer) {
        this.rideRepository = rideRepository;
        this.queueProducer = queueProducer;
    }

    public void execute(final UUID rideId){
        final var ride = rideRepository.getRideById(rideId);
        if (Objects.isNull(ride))
            throw new GatewayNotFoundError("Ride not found!");
        ride.finish();
        this.rideRepository.update(ride);
        this.queueProducer.sendMessage("QUEUE_PROCESS_PAYMENT", rideId);
    }
}
