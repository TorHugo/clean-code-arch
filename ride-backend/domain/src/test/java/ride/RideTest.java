package ride;

import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.dev.torhugo.clean.code.arch.domain.utils.RideStatusEnumUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class RideTest {

    @Test
    void shouldInstantiateRideWhenValidParams(){
        // Given
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = REQUESTED.getDescription();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNull(actualRide.getDistance());
        assertNull(actualRide.getDriverId());
        assertNotNull(actualRide.getCreatedAt());
        assertNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldRestoreRideWithSuccess(){
        // Given
        final var expectedRideId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedDriverId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedLastLat = Math.random();
        final var expectedLastLong = Math.random();
        final var expectedStatus = REQUESTED.getDescription();
        final var expectedCreatedAt = LocalDateTime.now();

        // When
        final var actualRide = Ride.restore(
                expectedRideId,
                expectedPassengerId,
                expectedDriverId,
                expectedFromLat,
                expectedFromLong,
                expectedToLat,
                expectedToLong,
                expectedStatus,
                null,
                expectedLastLat,
                expectedLastLong,
                null,
                expectedCreatedAt,
                null);

        // Then
        assertEquals(expectedRideId, actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertEquals(expectedCreatedAt, actualRide.getCreatedAt());
        assertNull(actualRide.getFare());
        assertNull(actualRide.getDistance());
        assertNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldAcceptWithSuccess(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = ACCEPTED.getDescription();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNull(actualRide.getDistance());
        assertNotNull(actualRide.getCreatedAt());
        assertNotNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldAcceptThrowExceptionWhenInvalidStatus(){
        // Given
        final var expectedError = "Invalid status!";
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                actualRide.accept(expectedDriverId));

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void shouldStartWithSuccess(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = IN_PROGRESS.getDescription();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);
        actualRide.start();

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNull(actualRide.getDistance());
        assertNotNull(actualRide.getCreatedAt());
        assertNotNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldStartThrowExceptionWhenInvalidStatus(){
        // Given
        final var expectedError = "Invalid status!";
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        final var exception = assertThrows(InvalidArgumentError.class, actualRide::start);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void shouldUpdatePositionWithSuccess(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = IN_PROGRESS.getDescription();

        final var expectedNewLatitude = Math.random();
        final var expectedNewLongitude = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);
        actualRide.start();
        actualRide.updatePosition(expectedNewLatitude, expectedNewLongitude);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNotNull(actualRide.getDistance());
        assertNotNull(actualRide.getCreatedAt());
        assertNotNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldUpdatePositionWhenTwoUpdateSuccess(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = IN_PROGRESS.getDescription();

        final var expectedNewLatitude = Math.random();
        final var expectedNewLongitude = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);
        actualRide.start();
        actualRide.updatePosition(expectedNewLatitude, expectedNewLongitude);
        actualRide.updatePosition(expectedNewLatitude, expectedNewLongitude);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNotNull(actualRide.getDistance());
        assertNotNull(actualRide.getLastPosition());
        assertNotNull(actualRide.getCreatedAt());
        assertNotNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldUpdatePositionThrowExceptionWhenInvalidStatus(){
        // Given
        final var expectedError = "Could not update position!";
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var expectedNewLatitude = Math.random();
        final var expectedNewLongitude = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);

        final var exception = assertThrows(InvalidArgumentError.class, () -> actualRide.updatePosition(expectedNewLatitude, expectedNewLongitude));

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedError, exception.getMessage());
        assertNull(actualRide.getDistance());
    }

    @Test
    void shouldFinishRideWithSuccess(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedStatus = COMPLETED.getDescription();
        final var expectedNewLatitude = Math.random();
        final var expectedNewLongitude = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);
        actualRide.start();
        actualRide.updatePosition(expectedNewLatitude, expectedNewLongitude);
        actualRide.finish();

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedDriverId, actualRide.getDriverId());
        assertEquals(expectedFromLat, actualRide.getFrom().getLatitude());
        assertEquals(expectedFromLong, actualRide.getFrom().getLongitude());
        assertEquals(expectedToLat, actualRide.getTo().getLatitude());
        assertEquals(expectedToLong, actualRide.getTo().getLongitude());
        assertEquals(expectedStatus, actualRide.getStatus());
        assertNotNull(actualRide.getFare());
        assertNotNull(actualRide.getDistance());
        assertNotNull(actualRide.getCreatedAt());
        assertNotNull(actualRide.getUpdatedAt());
    }

    @Test
    void shouldFinishRideThrowExceptionWhenInvalidStatus(){
        // Given
        final var expectedDriverId = UUID.randomUUID();
        final var expectedPassengerId = UUID.randomUUID();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedError = "Invalid Status!";
        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);
        actualRide.accept(expectedDriverId);

        final var exception = assertThrows(InvalidArgumentError.class, actualRide::finish);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedError, exception.getMessage());
        assertEquals(BigDecimal.ZERO, actualRide.getFare());
        assertNull(actualRide.getDistance());
    }
}
