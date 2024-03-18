package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideInput;
import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.gateway.models.AccountDTO;
import com.dev.torhugo.clean.code.arch.application.singup.mock.MockDsl;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.application.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AcceptRideUseCaseTest implements MockDsl {
    @Mock
    AccountGateway accountGateway;
    @Mock
    RideRepository rideRepository;
    @InjectMocks
    AcceptRideUseCase acceptRideUseCase;

    private AccountDTO passenger;
    private AccountDTO driver;
    @BeforeEach
    void setUp() {
        passenger = new AccountDTO(UUID.randomUUID(), "account account", "account@test.com", "648.808.745-23", true, false, "ABC1234", LocalDateTime.now(), null);
        driver = new AccountDTO(UUID.randomUUID(), "account account", "account@test.com", "648.808.745-23", false, true, "ABC1234", LocalDateTime.now(), null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteRequestRideWithSuccess(){
        // Given
        final var expectedAccount = createAccountDriver(LocalDateTime.now(), null);
        final var expectedPassengerId = expectedAccount.accountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);

        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideRepository.getAllRidesWithStatus(any(), anyBoolean(), any())).thenReturn(new ArrayList<>());
        // When
        this.acceptRideUseCase.execute(expectedInput);

        // Then
        verify(rideRepository, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideRepository, times(1)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsPassenger(){
        // Given
        final var expectedError = "This account not driver!";
        final var expectedAccount = createAccountPassender(LocalDateTime.now(), null);

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);
        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideRepository, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideRepository, times(0)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideRepository, times(0)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDriverContainsUnfinishedRides(){
        // Given
        final var expectedError = "This driver contains unfinished rides!";
        final var expectedAccount = createAccountDriver(LocalDateTime.now(), null);

        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedInput = new AcceptRideInput(expectedRide.getRideId(), expectedPassengerId);
        when(this.rideRepository.getRideById(expectedRide.getRideId())).thenReturn(expectedRide);
        when(this.accountGateway.getByAccountId(any())).thenReturn(expectedAccount);
        when(this.rideRepository.getAllRidesWithStatus(any(), anyBoolean(), any())).thenReturn(List.of(expectedRide));

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                this.acceptRideUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(rideRepository, times(1)).getRideById(any());
        verify(accountGateway, times(1)).getByAccountId(any());
        verify(rideRepository, times(1)).getAllRidesWithStatus(any(), anyBoolean(), any());
        verify(rideRepository, times(0)).save(any());
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
