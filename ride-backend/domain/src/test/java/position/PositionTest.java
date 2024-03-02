package position;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.domain.vo.Coord;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class PositionTest {

    @Test
    void shouldInstantiatePositionWhenValidParams(){
        // Given
        final var expectedRideId = UUID.randomUUID();
        final var expectedLatitude = Math.random();
        final var expectedLongitude = Math.random();
        final var expectedCoord = new Coord(expectedLongitude, expectedLatitude);

        // When
        final var actualPosition = Position.create(expectedRideId, expectedCoord.getLatitude(), expectedCoord.getLongitude());

        // Then
        assertNotNull(actualPosition.getPositionId());
        assertEquals(expectedRideId, actualPosition.getRideId());
        assertEquals(expectedCoord.getLatitude(), actualPosition.getCoord().getLatitude());
        assertEquals(expectedCoord.getLongitude(), actualPosition.getCoord().getLongitude());
        assertNotNull(actualPosition.getCreatedAt());
        assertNull(actualPosition.getUpdatedAt());
    }

    @Test
    void shouldRestorePositionWhenValidParams(){
        // Given
        final var expectedPositionId = UUID.randomUUID();
        final var expectedRideId = UUID.randomUUID();
        final var expectedLatitude = Math.random();
        final var expectedLongitude = Math.random();
        final var expectedCoord = new Coord(expectedLongitude, expectedLatitude);
        final var expectedCreatedAt = LocalDateTime.now();
        final var expectedUpdatedAt = LocalDateTime.now();

        // When
        final var actualPosition = Position.restore(expectedPositionId, expectedRideId, expectedCoord.getLatitude(), expectedCoord.getLongitude(), expectedCreatedAt, expectedUpdatedAt);

        // Then
        assertEquals(expectedPositionId, actualPosition.getPositionId());
        assertEquals(expectedRideId, actualPosition.getRideId());
        assertEquals(expectedCoord.getLatitude(), actualPosition.getCoord().getLatitude());
        assertEquals(expectedCoord.getLongitude(), actualPosition.getCoord().getLongitude());
        assertEquals(expectedCreatedAt, actualPosition.getCreatedAt());
        assertEquals(expectedUpdatedAt, actualPosition.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenInvalidLatitude(){
        final var expectedError = "Invalid latitude!";
        final var expectedLatitude = 91.0;
        final var expectedLongitude = Math.random();

        final var exception = assertThrows(InvalidArgumentError.class, () -> {
            new Coord(expectedLongitude, expectedLatitude);
        });

        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidLongitude(){
        final var expectedError = "Invalid longitude!";
        final var expectedLatitude = Math.random();
        final var expectedLongitude = 181.0;

        final var exception = assertThrows(InvalidArgumentError.class, () -> {
            new Coord(expectedLongitude, expectedLatitude);
        });

        assertEquals(expectedError, exception.getMessage());
    }
}
