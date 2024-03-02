package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.singup.mock.MockDsl;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetRideUseCaseTest implements MockDsl {
    @Mock
    AccountGateway accountGateway;
    @Mock
    RideGateway rideGateway;
    @InjectMocks
    GetRideUseCase getRideUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteGetRideWithSuccess() {
        // Given
        final var expectedPassenger = createAccountPassender(LocalDateTime.now(), null);

        final var expectedPassengerId = expectedPassenger.accountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();

        when(this.rideGateway.getRideById(expectedRideId)).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(expectedPassengerId)).thenReturn(expectedPassenger);

        // When
        final var actualRide = this.getRideUseCase.execute(expectedRideId);

        // Then
        assertNotNull(actualRide);
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        assertEquals(expectedPassengerId, expectedRide.getPassengerId());
        assertEquals(expectedFromLat, expectedRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, expectedRide.getFrom().getLongitude());
        assertEquals(expectedToLat, expectedRide.getTo().getLatitude());
        assertEquals(expectedToLong, expectedRide.getTo().getLongitude());
        assertNotNull(expectedRide.getStatus());
        assertNotNull(expectedRide.getCreatedAt());
    }

    @Test
    void shouldExecuteGetRideCompletedWithSuccess() {
        // Given
        final var expectedPassenger = createAccountPassender(LocalDateTime.now(), null);
        final var expectedDriver = createAccountDriver(LocalDateTime.now(), null);

        final var expectedPassengerId = expectedPassenger.accountId();
        final var expectedDriverId = expectedDriver.accountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        expectedRide.accept(expectedDriver.accountId());
        final var expectedRideId = expectedRide.getRideId();

        when(this.rideGateway.getRideById(expectedRideId)).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(expectedPassengerId)).thenReturn(expectedPassenger);
        when(this.accountGateway.getByAccountId(expectedDriverId)).thenReturn(expectedDriver);

        // When
        final var actualRide = this.getRideUseCase.execute(expectedRideId);

        // Then
        assertNotNull(actualRide);
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(2)).getByAccountId(any());
        assertEquals(expectedPassengerId, expectedRide.getPassengerId());
        assertEquals(expectedFromLat, expectedRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, expectedRide.getFrom().getLongitude());
        assertEquals(expectedToLat, expectedRide.getTo().getLatitude());
        assertEquals(expectedToLong, expectedRide.getTo().getLongitude());
        assertNotNull(expectedRide.getStatus());
        assertNotNull(expectedRide.getCreatedAt());
    }

    @Test
    void shouldExecuteGetRideWhenRideNotFound() {
        // Given
        final var expectedError = "Ride not found!";
        when(this.rideGateway.getRideById(any())).thenReturn(null);

        // When
        final var exception = assertThrows(GatewayNotFoundError.class, () ->
                this.getRideUseCase.execute(any()));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(0)).getByAccountId(any());
    }

    @Test
    void shouldExecuteGetRideWhenPassengerNotFound() {
        // Given
        final var expectedError = "Passenger not found!";

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var expectedRideId = expectedRide.getRideId();

        when(this.rideGateway.getRideById(expectedRideId)).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(null);

        // When
        final var exception = assertThrows(GatewayNotFoundError.class, () ->
                this.getRideUseCase.execute(expectedRideId));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
    }

    @Test
    void shouldExecuteGetRideWhenDriverNotFound() {
        // Given
        final var expectedError = "Driver not found!";

        final var expectedPassenger = createAccountPassender(LocalDateTime.now(), null);
        final var expectedDriver = createAccountDriver(LocalDateTime.now(), null);

        final var expectedPassengerId = expectedPassenger.accountId();
        final var expectedDriverId = expectedDriver.accountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        expectedRide.accept(expectedDriver.accountId());
        final var expectedRideId = expectedRide.getRideId();

        when(this.rideGateway.getRideById(expectedRideId)).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(expectedPassengerId)).thenReturn(expectedPassenger);
        when(this.accountGateway.getByAccountId(expectedDriverId)).thenReturn(null);

        // When
        final var exception = assertThrows(GatewayNotFoundError.class, () ->
                this.getRideUseCase.execute(expectedRideId));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(2)).getByAccountId(any());
    }

}
