package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.domain.ride.RideGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetRideUseCaseTest {
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
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedPassenger = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedPassenger.getAccountId();
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
        assertEquals(expectedFromLat, expectedRide.getFromLat());
        assertEquals(expectedFromLong, expectedRide.getFromLong());
        assertEquals(expectedToLat, expectedRide.getToLat());
        assertEquals(expectedToLong, expectedRide.getToLong());
        assertNotNull(expectedRide.getStatus());
        assertNotNull(expectedRide.getCreatedAt());
    }

    @Test
    void shouldExecuteGetRideWhenRideNotFound() {
        // Given
        final var expectedError = "Ride not found!";
        when(this.rideGateway.getRideById(any())).thenReturn(null);

        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
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
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                this.getRideUseCase.execute(expectedRideId));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
    }

}
