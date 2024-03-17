import com.dev.torhugo.clean.code.arch.domain.enums.MessageEnum;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultTest {

    @Test
    void shouldInitializeGatewayNotFoundError(){
        // Given
        final var expectedException = "Exception!";
        // When
        final var actualException = new RepositoryNotFoundError("Exception!");
        // Then
        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void shouldRetrieveMessageWhenInvokedMessageEnum(){
        // Given
        final var expectedMessage = "Welcome to course clean code and clean architecture!";
        // When
        final var messageEnum = MessageEnum.WELCOME;
        // Then
        assertEquals(messageEnum.getMessage(), expectedMessage);
    }
}
