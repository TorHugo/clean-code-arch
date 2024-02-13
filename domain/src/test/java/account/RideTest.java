package account;

import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import org.junit.jupiter.api.Test;

import static com.dev.torhugo.clean.code.arch.domain.utils.IdentifierUtils.generateIdentifier;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RideTest {

    @Test
    void shouldInstantiateRideWhenValidParams(){
        // Given
        final var expectedPassengerId = generateIdentifier();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        // When
        final var actualRide = Ride.create(expectedPassengerId, expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        // Then
        assertNotNull(actualRide.getRideId());
        assertEquals(expectedPassengerId, actualRide.getPassengerId());
        assertEquals(expectedFromLat, actualRide.getFromLat());
        assertEquals(expectedFromLong, actualRide.getFromLong());
        assertEquals(expectedToLat, actualRide.getToLat());
        assertEquals(expectedToLong, actualRide.getToLong());
        assertNotNull(actualRide.getCreatedAt());
        assertNull(actualRide.getUpdatedAt());
    }
}
