package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideInput;
import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.application.gateway.RideGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AcceptRideUseCaseTest {
    @Mock
    AccountGateway accountGateway;
    @Mock
    RideGateway rideGateway;
    @InjectMocks
    AcceptRideUseCase acceptRideUseCase;
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
        final var carPlate = "ABC1234";
        final var isPassenger = false;
        final var isDriver = true;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, carPlate);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);

        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideGateway.getAllRidesWithStatus(any(), anyBoolean(), any())).thenReturn(new ArrayList<>());
        // When
        this.acceptRideUseCase.execute(expectedInput);

        // Then
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(1)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideGateway, times(1)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenRideNotFound(){
        // Given
        final var expectedError = "Ride not found!";

        final var expectedRideId = UUID.randomUUID();
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var carPlate = "ABC1234";
        final var isPassenger = false;
        final var isDriver = true;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, carPlate);

        final var expectedInput = new AcceptRideInput(expectedRideId, expectedAccount.getAccountId());
        when(this.rideGateway.getRideById(expectedRideId)).thenReturn(null);

        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(0)).getByAccountId(any());
        verify(rideGateway, times(0)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideGateway, times(0)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound(){
        // Given
        final var expectedError = "Passenger not found!";

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);
        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(null);

        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(0)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideGateway, times(0)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsPassenger(){
        // Given
        final var expectedError = "This account not driver!";

        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var carPlate = "ABC1234";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, carPlate);

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);
        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(0)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideGateway, times(0)).update(any());
    }

    @Test
    void shouldThrowExceptionWhenDriverContainsUnfinishedRides(){
        // Given
        final var expectedError = "This driver contains unfinished rides!";

        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var carPlate = "ABC1234";
        final var isPassenger = false;
        final var isDriver = true;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, carPlate);

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);
        when(this.rideGateway.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideGateway.getAllRidesWithStatus(any(), anyBoolean(), any())).thenReturn(List.of(expectedRide));

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideGateway, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideGateway, times(1)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideGateway, times(0)).update(any());
    }

    @Test
    void shouldInstantiateAcceptRideInput(){
        // Given
        final var expectedRideId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();

        // When
        final var actualObject = AcceptRideInput.with(expectedRideId, expectedDriverId);

        // Then
        assertEquals(expectedDriverId, actualObject.driverId());
        assertEquals(expectedRideId, actualObject.rideId());
    }
}
