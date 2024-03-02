package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionInput;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
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
    RideGateway rideGateway;

    @Mock
    PositionGateway positionGateway;

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

        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        this.updatePositionUseCase.execute(expectedInput);

        // Then
        assertNotNull(expectedRide.getDistance());
        assertNotNull(expectedRide.getUpdatedAt());
        assertEquals(expectedRide.getLastPosition().getLatitude(), expectedLatitude);
        assertEquals(expectedRide.getLastPosition().getLongitude(), expectedLongitude);
        verify(rideGateway, times(1)).getRideById(any());
        verify(rideGateway, times(1)).update(any());
        verify(positionGateway, times(1)).save(any());

        verify(rideGateway, times(1)).update(argThat(ride ->
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

        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        this.updatePositionUseCase.execute(expectedInput);

        // Then
        assertNotNull(expectedRide.getDistance());
        assertNotNull(expectedRide.getUpdatedAt());
        assertEquals(expectedRide.getLastPosition().getLatitude(), expectedLatitude);
        assertEquals(expectedRide.getLastPosition().getLongitude(), expectedLongitude);
        verify(rideGateway, times(1)).getRideById(any());
        verify(rideGateway, times(1)).update(any());
        verify(positionGateway, times(1)).save(any());

        verify(rideGateway, times(1)).update(argThat(ride ->
                Objects.nonNull(ride.getRideId())
                        && Objects.equals(ride.getLastPosition().getLatitude(), expectedLatitude)
                        && Objects.equals(ride.getLastPosition().getLongitude(), expectedLongitude)
                        && Objects.nonNull(ride.getUpdatedAt())
        ));
    }

    @Test
    void shouldThrowExceptionWhenRideNotFound() {
        // Given
        final var expectedError = "Ride not found!";

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

        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(null);
        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                this.updatePositionUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(this.rideGateway, times(1)).getRideById(any());
        verify(this.rideGateway, times(0)).update(any());
        verify(this.positionGateway, times(0)).save(any());
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

        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.updatePositionUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(this.rideGateway, times(1)).getRideById(any());
        verify(this.rideGateway, times(0)).update(any());
        verify(this.positionGateway, times(0)).save(any());
    }
}
