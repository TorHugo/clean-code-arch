package ds;

import com.dev.torhugo.clean.code.arch.domain.ds.FareCalculator;
import com.dev.torhugo.clean.code.arch.domain.ds.template.FareCalculatorFactory;
import com.dev.torhugo.clean.code.arch.domain.ds.template.NormalFareCalculator;
import com.dev.torhugo.clean.code.arch.domain.ds.template.OvernightFareCalculator;
import com.dev.torhugo.clean.code.arch.domain.ds.template.SundayFareCalculator;
import config.DefaultTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FareCalculatorTest extends DefaultTest {
    @Test
    void ShouldFareCalculateWhenNormalDay(){
        // Given
        final var expectedDistance = Math.random();
        final var expectedDate = LocalDateTime.of(2024, 3, 18, 12, 0, 0);
        final var expectedFare = new BigDecimal("2.1");
        final var fareCalculator = FareCalculatorFactory.create(expectedDate);

        // When
        final var result = fareCalculator.calculate(expectedDistance);

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(expectedFare, fareCalculator.getFare()), expectedMessageToEqual);
    }
    @Test
    void ShouldFareCalculateWhenOvernight(){
        // Given
        final var expectedDistance = Math.random();
        final var expectedDate = LocalDateTime.of(2024, 3, 18, 23, 0, 0);
        final var expectedFare = new BigDecimal("3.9");
        final var fareCalculator = FareCalculatorFactory.create(expectedDate);

        // When
        final var result = fareCalculator.calculate(expectedDistance);

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(expectedFare, fareCalculator.getFare()), expectedMessageToEqual);
    }
    @Test
    void ShouldFareCalculateWhenSunday(){
        // Given
        final var expectedDistance = Math.random();
        final var expectedDate = LocalDateTime.of(2024, 3, 17, 12, 0, 0);
        final var expectedFare = new BigDecimal("2.9");
        final var fareCalculator = FareCalculatorFactory.create(expectedDate);

        // When
        final var result = fareCalculator.calculate(expectedDistance);

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(expectedFare, fareCalculator.getFare()), expectedMessageToEqual);
    }
}
