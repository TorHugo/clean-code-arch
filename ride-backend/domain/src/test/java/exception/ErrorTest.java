package exception;

import com.dev.torhugo.clean.code.arch.domain.error.exception.ResourceAccessError;
import config.DefaultTest;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorTest extends DefaultTest {
    @Test
    void shouldInitializeGatewayNotFoundError(){
        // Given
        final var expectedException = "Exception!";

        // When
        final var actualException = new GatewayNotFoundError("Exception!");

        // Then
        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void shouldInitializeResourceAccessError(){
        // Given
        final var expectedException = "Exception!";

        // When
        final var actualException = new ResourceAccessError("Exception!");

        // Then
        assertEquals(expectedException, actualException.getMessage());
    }
}
