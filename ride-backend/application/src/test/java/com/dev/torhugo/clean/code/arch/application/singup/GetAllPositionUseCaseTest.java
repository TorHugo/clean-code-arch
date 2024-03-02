package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.gateway.PositionGateway;
import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.getallposition.GetPositionOutput;
import com.dev.torhugo.clean.code.arch.domain.entity.Position;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class GetAllPositionUseCaseTest {
    @Mock
    PositionGateway positionGateway;
    @InjectMocks
    GetAllPositionUseCase getAllPositionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteGetAllPositionWithSuccess(){
        // Given
        final var expectedRideId = UUID.randomUUID();
        final var expectedLatitude = 90.0;
        final var expectedLongitude = 180.0;

        final var expectedPosition = Position.create(expectedRideId, expectedLatitude, expectedLongitude);
        final var lsPosition = List.of(expectedPosition);

        final var expectedPositionOutput = lsPosition.stream().map(GetPositionOutput::with).toList();
        when(positionGateway.retrieveByRideId(expectedRideId)).thenReturn(lsPosition);

        // When
        final var output = getAllPositionUseCase.execute(expectedRideId);

        // Then
        assertEquals(output.rideId(), expectedRideId);
        assertEquals(output.lsPositions(), expectedPositionOutput);
    }

    @Test
    void shouldThrowExceptionWhenLsPositionNotFound(){
        // Given
        final var expectedRideId = UUID.randomUUID();
        final var expectedException = "Positions not found!";

        when(positionGateway.retrieveByRideId(expectedRideId)).thenReturn(new ArrayList<>());

        // When
        final var exception = assertThrows(GatewayNotFoundError.class, () ->
                getAllPositionUseCase.execute(expectedRideId));

        // Then
        assertEquals(expectedException, exception.getMessage());
    }

}
