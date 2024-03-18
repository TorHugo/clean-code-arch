package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FinishRideUseCaseTest {

    @Mock
    RideRepository rideRepository;
    @Mock
    QueueProducer queueProducer;

    @InjectMocks
    FinishRideUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteUpdatePositionWithSuccess(){
        // Given
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();
        final var expectedStatus = "COMPLETED";
        expectedRide.accept(expectedDriverId);
        expectedRide.start();
        expectedRide.updatePosition(Math.random(), Math.random());

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        doNothing().when(this.queueProducer).sendMessage(anyString(), any());
        // When
        this.useCase.execute(expectedRideId);

        // Then
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(1)).save(any());
        assertNotNull(expectedRide.getFare());
        assertEquals(expectedRide.getStatus(), expectedStatus);
    }

    @Test
    void shouldThrowExceptionWhenInvalidStatus() {
        // Given
        final var expectedError = "Invalid Status!";
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();
        expectedRide.accept(expectedDriverId);

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.useCase.execute(expectedRideId));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(this.rideRepository, times(1)).getRideById(any());
        verify(this.rideRepository, times(0)).save(any());
    }
}
