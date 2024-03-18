package integration;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.PositionRepositoryImpl;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.RideRepositoryImpl;
import config.AnnotationDefaultIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;
import java.util.UUID;

import static mock.MockDefaultIT.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RideIntegrationTest extends AnnotationDefaultIT {
    @MockBean
    private AccountGateway accountGateway;
    @Autowired
    private PositionRepositoryImpl positionRepository;
    @Autowired
    private RideRepositoryImpl rideRepository;
    @Autowired
    private RequestRideUseCase requestRideUseCase;
    @Autowired
    private AcceptRideUseCase acceptRideUseCase;
    @Autowired
    private StartRideUseCase startRideUseCase;
    @Autowired
    private UpdatePositionUseCase updatePositionUseCase;
    @Autowired
    private FinishRideUseCase finishRideUseCase;

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRequestRideWithSuccess() {
        // Given
        final var expectedRequestRide = generateRequestRide();
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountPassenger(generateRequestRide().passengerId()));

        // When
        final var expectedRideId = this.requestRideUseCase.execute(expectedRequestRide);
        assertNotNull(expectedRideId);
        final var actualRide = this.rideRepository.getRideById(UUID.fromString(expectedRideId));
        assertNotNull(actualRide);

        // Then
        assertEquals(expectedTrue, Objects.equals(expectedRequestRide.passengerId(), actualRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRide.from().latitude(), actualRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRide.from().longitude(), actualRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRide.to().latitude(), actualRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRide.to().longitude(), actualRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("REQUESTED", actualRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getCreatedAt()), expectedMessageNonNull);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldAcceptedRideWithSuccess() {
        // Given
        // request-ride
        final var expectedRequestRideInput = generateRequestRide();
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountPassenger(generateRequestRide().passengerId()));
        final var expectedRideId = this.requestRideUseCase.execute(expectedRequestRideInput);
        assertNotNull(expectedRideId);

        // accept-ride
        final var expectedAcceptRideInput = generateAcceptRideInput(UUID.fromString(expectedRideId));
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountDriver(expectedAcceptRideInput.driverId()));
        this.acceptRideUseCase.execute(expectedAcceptRideInput);

        // When
        final var actualRide = this.rideRepository.getRideById(UUID.fromString(expectedRideId));
        assertNotNull(actualRide);

        // Then
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.passengerId(), actualRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedAcceptRideInput.driverId(), actualRide.getDriverId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().latitude(), actualRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().longitude(), actualRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().latitude(), actualRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().longitude(), actualRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("ACCEPTED", actualRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getCreatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getUpdatedAt()), expectedMessageNonNull);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldStartRideWithSuccess() {
        // Given
        // request-ride
        final var expectedRequestRideInput = generateRequestRide();
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountPassenger(generateRequestRide().passengerId()));
        final var expectedRideId = this.requestRideUseCase.execute(expectedRequestRideInput);
        assertNotNull(expectedRideId);

        // accept-ride
        final var expectedAcceptRideInput = generateAcceptRideInput(UUID.fromString(expectedRideId));
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountDriver(expectedAcceptRideInput.driverId()));
        this.acceptRideUseCase.execute(expectedAcceptRideInput);

        // start-ride
        this.startRideUseCase.execute(UUID.fromString(expectedRideId));

        // When
        final var actualRide = this.rideRepository.getRideById(UUID.fromString(expectedRideId));
        assertNotNull(actualRide);

        // Then
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.passengerId(), actualRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedAcceptRideInput.driverId(), actualRide.getDriverId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().latitude(), actualRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().longitude(), actualRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().latitude(), actualRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().longitude(), actualRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("IN_PROGRESS", actualRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getCreatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getUpdatedAt()), expectedMessageNonNull);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldUpdatePositionRideWithSuccess() {
        // Given
        // request-ride
        final var expectedRequestRideInput = generateRequestRide();
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountPassenger(generateRequestRide().passengerId()));
        final var expectedRideId = this.requestRideUseCase.execute(expectedRequestRideInput);
        assertNotNull(expectedRideId);

        // accept-ride
        final var expectedAcceptRideInput = generateAcceptRideInput(UUID.fromString(expectedRideId));
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountDriver(expectedAcceptRideInput.driverId()));
        this.acceptRideUseCase.execute(expectedAcceptRideInput);

        // start-ride
        this.startRideUseCase.execute(UUID.fromString(expectedRideId));

        // update-position
        final var expectedPosition = 1;
        final var expectedUpdatePositionInput = generateUpdatePositionInput(UUID.fromString(expectedRideId));
        this.updatePositionUseCase.execute(expectedUpdatePositionInput);

        // When
        final var actualRide = this.rideRepository.getRideById(UUID.fromString(expectedRideId));
        assertNotNull(actualRide);
        final var positions = this.positionRepository.retrieveByRideId(UUID.fromString(expectedRideId));
        assertEquals(expectedTrue, Objects.equals(positions.size(), expectedPosition), expectedMessageToEqual);
        final var position = positions.get(0);

        // Then
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.passengerId(), actualRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedAcceptRideInput.driverId(), actualRide.getDriverId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().latitude(), actualRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().longitude(), actualRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().latitude(), actualRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().longitude(), actualRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("IN_PROGRESS", actualRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getCreatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getUpdatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.equals(position.getCoord().getLatitude(), expectedUpdatePositionInput.latitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(position.getCoord().getLongitude(), expectedUpdatePositionInput.longitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getDistance()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getLastPosition()), expectedMessageNonNull);
    }



    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldFinishRideWithSuccess() {
        // Given
        // request-ride
        final var expectedRequestRideInput = generateRequestRide();
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountPassenger(generateRequestRide().passengerId()));
        final var expectedRideId = this.requestRideUseCase.execute(expectedRequestRideInput);
        assertNotNull(expectedRideId);

        // accept-ride
        final var expectedAcceptRideInput = generateAcceptRideInput(UUID.fromString(expectedRideId));
        when(accountGateway.getByAccountId(any()))
                .thenReturn(generateAccountDriver(expectedAcceptRideInput.driverId()));
        this.acceptRideUseCase.execute(expectedAcceptRideInput);

        // start-ride
        this.startRideUseCase.execute(UUID.fromString(expectedRideId));

        // update-position
        final var expectedUpdatePositionInput = generateUpdatePositionInput(UUID.fromString(expectedRideId));
        this.updatePositionUseCase.execute(expectedUpdatePositionInput);

        // finish-ride
        this.finishRideUseCase.execute(UUID.fromString(expectedRideId));

        // When
        final var actualRide = this.rideRepository.getRideById(UUID.fromString(expectedRideId));
        assertNotNull(actualRide);

        // Then
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.passengerId(), actualRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedAcceptRideInput.driverId(), actualRide.getDriverId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().latitude(), actualRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.from().longitude(), actualRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().latitude(), actualRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedRequestRideInput.to().longitude(), actualRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("COMPLETED", actualRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getCreatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getUpdatedAt()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getDistance()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getLastPosition()), expectedMessageNonNull);
        assertEquals(expectedTrue, Objects.nonNull(actualRide.getFare()), expectedMessageNonNull);
    }
}
