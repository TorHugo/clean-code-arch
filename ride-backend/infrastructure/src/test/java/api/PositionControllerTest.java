package api;

import com.dev.torhugo.clean.code.arch.application.getallposition.GetAllPositionUseCase;
import com.dev.torhugo.clean.code.arch.application.updateposition.UpdatePositionUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.UpdatePositionRequest;
import config.ControllerDefaultIT;
import mock.MockDefaultIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PositionControllerTest extends ControllerDefaultIT {
    @MockBean
    private UpdatePositionUseCase updatePositionUseCase;
    @MockBean
    private GetAllPositionUseCase getAllPositionUseCase;
    @BeforeEach
    void beforeSetup() {
    }

    @Test
    void shouldUpdatePositionWithSuccess() throws Exception {
        // Given
        final var expectedRideId = MockDefaultIT.generateIdentifier();
        final var expectedCoordinate = MockDefaultIT.generateCoordinateInfo();
        final var expectedInput = new UpdatePositionRequest(expectedRideId, expectedCoordinate.latitude(), expectedCoordinate.longitude());
        doNothing().when(this.updatePositionUseCase).execute(any());

        // When
        final var request = MockMvcRequestBuilders
                .post("/positions/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(expectedInput));

        final var response = super.mvc
                        .perform(request)
                        .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.ride_id", equalTo(expectedRideId.toString())));
        verify(this.updatePositionUseCase, times(1)).execute(any());
    }

    @Test
    void shouldGetAllPositionWithSuccess() throws Exception {
        // Given
        final var expectedRideId = MockDefaultIT.generateIdentifier();
        final var expectedInput = MockDefaultIT.generateGetAllPositionOutput(expectedRideId);
        when(this.getAllPositionUseCase.execute(expectedRideId))
                .thenReturn(expectedInput);

        // When
        final var request = MockMvcRequestBuilders
                .get("/positions/all/{rideId}", expectedRideId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());
        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.ride_id", equalTo(expectedRideId.toString())))
                .andExpect(jsonPath("$.positions[0].coordinates.latitude").value(notNullValue()))
                .andExpect(jsonPath("$.positions[0].coordinates.longitude").value(notNullValue()))
                .andExpect(jsonPath("$.positions[0].created_at").value(notNullValue()));
        verify(this.getAllPositionUseCase, times(1)).execute(expectedRideId);
    }

}
