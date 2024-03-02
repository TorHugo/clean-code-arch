import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultTest {

    @Test
    void shouldInitializeDatabaseNotFoundError(){
        // Given
        final var expectedException = "Exception!";

        // When
        final var actualException = new GatewayNotFoundError("Exception!");

        // Then
        assertEquals(expectedException, actualException.getMessage());
    }
}
