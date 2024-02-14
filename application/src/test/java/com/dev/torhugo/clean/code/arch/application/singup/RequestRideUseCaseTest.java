package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestRideUseCaseTest {
    @Mock
    AccountGateway accountGateway;
    @Mock
    RideGateway rideGateway;
    @InjectMocks
    RequestRideUseCase requestRideUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteRequestRideWithSuccess(){
        // Given
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.from(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.from(expectedToLat, expectedToLong));
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideGateway.getActiveRidesByPassengerId(any())).thenReturn(new ArrayList<>());

        // When
        final var actualRide = requestRideUseCase.execute(input);

        // Then
        assertNotNull(actualRide);
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(1)).getActiveRidesByPassengerId(any());
        verify(rideGateway, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound(){
        // Given
        final var expectedError = "Account not found!";
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.from(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.from(expectedToLat, expectedToLong));
        when(this.accountGateway.getByAccountId(any())).thenReturn(null);
        when(this.rideGateway.getActiveRidesByPassengerId(any())).thenReturn(new ArrayList<>());

        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                requestRideUseCase.execute(input));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(0)).getActiveRidesByPassengerId(any());
        verify(rideGateway, times(0)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsNotPassenger(){
        // Given
        final var expectedError = "Account is not passenger!";
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = false;
        final var isDriver = true;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.from(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.from(expectedToLat, expectedToLong));
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideGateway.getActiveRidesByPassengerId(any())).thenReturn(new ArrayList<>());

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                requestRideUseCase.execute(input));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(0)).getActiveRidesByPassengerId(any());
        verify(rideGateway, times(0)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsActiveRides(){
        // Given
        final var expectedError = "Passenger has an active ride!";
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.from(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.from(expectedToLat, expectedToLong));
        final var ride = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideGateway.getActiveRidesByPassengerId(any())).thenReturn(List.of(ride));

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                requestRideUseCase.execute(input));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(1)).getActiveRidesByPassengerId(any());
        verify(rideGateway, times(0)).save(any());
    }
}
