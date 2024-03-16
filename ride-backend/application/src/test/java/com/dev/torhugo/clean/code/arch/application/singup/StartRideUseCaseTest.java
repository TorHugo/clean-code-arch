package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StartRideUseCaseTest {
    @Mock
    RideRepository rideRepository;
    @InjectMocks
    StartRideUseCase startRideUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteStartRideWithSuccess(){
        // Given
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        expectedRide.accept(expectedDriverId);

        final var expectedInput = expectedRide.getRideId();

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        this.startRideUseCase.execute(expectedInput);

        // Then
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(1)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenRideNotFound(){
        // Given
        final var expectedException = "Ride not found!";
        when(this.rideRepository.getRideById(any())).thenReturn(null);

        // When
        final var exception = assertThrows(GatewayNotFoundError.class, () ->
                this.startRideUseCase.execute(any()));


        // Then
        assertEquals(expectedException, exception.getMessage());
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(0)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenInvalidStatus(){
        // Given
        final var expectedException = "Invalid status!";
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedInput = expectedRide.getRideId();

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);

        // When

        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.startRideUseCase.execute(expectedInput));


        // Then
        assertEquals(expectedException, exception.getMessage());
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(0)).update(any());
    }
}
