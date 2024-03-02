import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultTest {

    @Test
    void shouldInitializeDatabaseNotFoundError(){
        // Given
        final var expectedException = "Exception!";

        // When
        final var actualException = new DatabaseNotFoundError("Exception!");

        // Then
        assertEquals(expectedException, actualException.getMessage());
    }
}
