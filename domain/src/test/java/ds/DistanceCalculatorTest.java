package ds;

import com.dev.torhugo.clean.code.arch.domain.ds.DistanceCalculator;
import com.dev.torhugo.clean.code.arch.domain.vo.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceCalculatorTest {

    @Test
    void shouldCalculateDistance(){
        // Given
        final var expectedFrom = new Coord(20.0, -30.0);
        final var expectedTo = new Coord(-15.0, -25.0);
        final var expectedDistance = 3483.0;

        // When
        final var distance = DistanceCalculator.calculate(expectedFrom, expectedTo);

        // Then
        assertEquals(distance, expectedDistance);
    }
}
