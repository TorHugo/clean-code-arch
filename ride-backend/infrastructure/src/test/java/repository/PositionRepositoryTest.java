package repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.PositionRepositoryImpl;
import config.AnnotationDefaultIT;
import mock.MockDefaultIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PositionRepositoryTest extends AnnotationDefaultIT {
    @Autowired
    private PositionRepositoryImpl positionRepository;
    private Position positionObject;
    @BeforeEach
    void beforeSetup() {
        positionObject = MockDefaultIT.generateObjectPosition();
        this.positionRepository.save(positionObject);
    }
    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRetrieveAllPositionsWithSuccess() {
        // Given && When
        final var result = this.positionRepository.retrieveByRideId(positionObject.getRideId());

        // Then
        assertNotNull(result);
        final var position = result.get(0);
        assertEquals(expectedTrue, Objects.equals(positionObject.getRideId(), position.getRideId()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(positionObject.getCoord().getLatitude(), position.getCoord().getLatitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.equals(positionObject.getCoord().getLongitude(), position.getCoord().getLongitude()), expectedMessageToEqual);
        assertEquals(expectedTrue, Objects.nonNull(position.getCreatedAt()), expectedMessageNonNull);
    }
}
