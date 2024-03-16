package com.dev.torhugo.clean.code.arch.application.processpayment;

import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;

import java.util.UUID;

public class ProcessPaymentUseCase {
    private final RideRepository rideRepository;
    public ProcessPaymentUseCase(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
    public void execute(final UUID rideId){
        System.out.println("[ProcessPayment] execute()");
    }
}
