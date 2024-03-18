package repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.RideRepositoryImpl;
import config.AnnotationDefaultIT;
import mock.MockDefaultIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RideRepositoryTest extends AnnotationDefaultIT {
    @Autowired
    private RideRepositoryImpl rideRepository;
    private Ride rideObject;
    @BeforeEach
    void beforeSetup() {
        rideObject = MockDefaultIT.generateObjectRide();
        this.rideRepository.save(rideObject);
    }
    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRetrieveRideRideWithSuccess() {
        // Given && When
        final var result = this.rideRepository.getRideById(rideObject.getRideId());

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(rideObject.getRideId(), result.getRideId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getPassengerId(), result.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLatitude(), result.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLongitude(), result.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLatitude(), result.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLongitude(), result.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals("REQUESTED", result.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(result.getCreatedAt()), expectedMessageNonNull);
    }
    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldGetAllRidesWithStatusWhenAccountIsPassenger(){
        // Given
        final var expectedResult = 1;
        final var expectedStatus = "REQUESTED";

        // When
        final var result = this.rideRepository.getAllRidesWithStatus(rideObject.getPassengerId(), true, "REQUESTED");

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(expectedResult, result.size()));

        final var expectedRide = result.get(0);
        assertEquals(expectedTrue, Objects.equals(rideObject.getRideId(), expectedRide.getRideId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getPassengerId(), expectedRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getDriverId(), expectedRide.getDriverId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLatitude(), expectedRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLongitude(), expectedRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLatitude(), expectedRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLongitude(), expectedRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedStatus, expectedRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(expectedRide.getCreatedAt()), expectedMessageNonNull);
    }
    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldGetAllRidesWithStatusWhenAccountIsDriver(){
        // Given
        final var expectedResult = 1;
        final var expectedStatus = "ACCEPTED";
        rideObject.accept(UUID.randomUUID());
        this.rideRepository.save(rideObject);

        // When
        final var result = this.rideRepository.getAllRidesWithStatus(rideObject.getDriverId(), false, "ACCEPTED");

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(expectedResult, result.size()));

        final var expectedRide = result.get(0);
        assertEquals(expectedTrue, Objects.equals(rideObject.getRideId(), expectedRide.getRideId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getPassengerId(), expectedRide.getPassengerId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLatitude(), expectedRide.getFrom().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getFrom().getLongitude(), expectedRide.getFrom().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLatitude(), expectedRide.getTo().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(rideObject.getTo().getLongitude(), expectedRide.getTo().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(expectedStatus, expectedRide.getStatus()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(expectedRide.getCreatedAt()), expectedMessageNonNull);
    }
}
