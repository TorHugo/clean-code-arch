package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionInput;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.application.repository.PositionRepository;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdatePositionUseCaseTest {

    @Mock
    RideRepository rideRepository;

    @Mock
    PositionRepository positionRepository;

    @InjectMocks
    UpdatePositionUseCase updatePositionUseCase;

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
        expectedRide.accept(expectedDriverId);
        expectedRide.start();

        final var expectedLatitude = Math.random();
        final var expectedLongitude = Math.random();
        final var expectedInput = UpdatePositionInput.with(expectedRideId, expectedLatitude, expectedLongitude);

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        this.updatePositionUseCase.execute(expectedInput);

        // Then
        assertNotNull(expectedRide.getDistance());
        assertNotNull(expectedRide.getUpdatedAt());
        assertEquals(expectedRide.getLastPosition().getLatitude(), expectedLatitude);
        assertEquals(expectedRide.getLastPosition().getLongitude(), expectedLongitude);
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(1)).save(any());
        verify(positionRepository, times(1)).save(any());

        verify(rideRepository, times(1)).save(argThat(ride ->
                Objects.nonNull(ride.getRideId())
                        && Objects.equals(ride.getLastPosition().getLatitude(), expectedLatitude)
                        && Objects.equals(ride.getLastPosition().getLongitude(), expectedLongitude)
                        && Objects.nonNull(ride.getUpdatedAt())
        ));
    }

    @Test
    void shouldExecuteUpdatePositionWhenDistanceSuccess(){
        // Given
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();
        expectedRide.accept(expectedDriverId);
        expectedRide.start();

        final var expectedLatitude = Math.random();
        final var expectedLongitude = Math.random();
        final var expectedInput = UpdatePositionInput.with(expectedRideId, expectedLatitude, expectedLongitude);
        expectedRide.updatePosition(expectedLatitude, expectedLongitude);

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        this.updatePositionUseCase.execute(expectedInput);

        // Then
        assertNotNull(expectedRide.getDistance());
        assertNotNull(expectedRide.getUpdatedAt());
        assertEquals(expectedRide.getLastPosition().getLatitude(), expectedLatitude);
        assertEquals(expectedRide.getLastPosition().getLongitude(), expectedLongitude);
        verify(rideRepository, times(1)).getRideById(any());
        verify(rideRepository, times(1)).save(any());
        verify(positionRepository, times(1)).save(any());

        verify(rideRepository, times(1)).save(argThat(ride ->
                Objects.nonNull(ride.getRideId())
                        && Objects.equals(ride.getLastPosition().getLatitude(), expectedLatitude)
                        && Objects.equals(ride.getLastPosition().getLongitude(), expectedLongitude)
                        && Objects.nonNull(ride.getUpdatedAt())
        ));
    }
    @Test
    void shouldThrowExceptionWhenInvalidStatus() {
        // Given
        final var expectedError = "Could not update position!";

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();
        expectedRide.accept(expectedDriverId);

        final var expectedLatitude = Math.random();
        final var expectedLongitude = Math.random();
        final var expectedInput = UpdatePositionInput.with(expectedRideId, expectedLatitude, expectedLongitude);

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.updatePositionUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(this.rideRepository, times(1)).getRideById(any());
        verify(this.rideRepository, times(0)).save(any());
        verify(this.positionRepository, times(0)).save(any());
    }
}
