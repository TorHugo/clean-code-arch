package com.dev.torhugo.clean.code.arch.application.finishride;

import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

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
        ride.finish();
        this.rideRepository.save(ride);
        this.queueProducer.sendMessage("QUEUE_PROCESS_PAYMENT", rideId);
    }
}
