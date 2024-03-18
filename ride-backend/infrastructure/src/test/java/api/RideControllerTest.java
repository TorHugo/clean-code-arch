package api;

import com.dev.torhugo.clean.code.arch.application.acceptride.AcceptRideUseCase;
import com.dev.torhugo.clean.code.arch.application.finishride.FinishRideUseCase;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.application.startride.StartRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.error.exception.GatewayNotFoundError;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideRequest;
import config.ControllerDefaultIT;
import mock.MockDefaultIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RideControllerTest extends ControllerDefaultIT {
    private static UUID passengerId;
    private static GetRideOutput responseRequested;
    private static GetRideOutput responseAccepted;

    @MockBean
    private RequestRideUseCase requestRideUseCase;
    @MockBean
    private GetRideUseCase getRideUseCase;
    @MockBean
    private AcceptRideUseCase acceptRideUseCase;
    @MockBean
    private StartRideUseCase startRideUseCase;
    @MockBean
    private FinishRideUseCase finishRideUseCase;

    @BeforeEach
    void beforeSetup() {
        passengerId = MockDefaultIT.generateIdentifier();
        responseRequested = MockDefaultIT.generateResponseRideRequested();
        responseAccepted = MockDefaultIT.generateResponseRideAccepted();
    }

    @Test
    void shouldRequestRideWithSuccess() throws Exception {
        // Given
        final var expectedRideId = MockDefaultIT.generateIdentifier();
        final var expectedInput = new RideRequest(passengerId, MockDefaultIT.generateCoordinateInfo(), MockDefaultIT.generateCoordinateInfo());
        when(requestRideUseCase.execute(any())).thenReturn(expectedRideId.toString());

        // When
        final var request = MockMvcRequestBuilders
                .post("/rides/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(expectedInput));

        final var response = super.mvc
                        .perform(request)
                        .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.ride_id", equalTo(expectedRideId.toString())));
        verify(requestRideUseCase, times(1)).execute(any());
    }

    @Test
    void shouldThrowExceptionWhenRideRequestedAndAccountIsNotPassenger() throws Exception {
        // Given
        final var expectedMessage = "Account not found! AccountId: " + passengerId;
        final var expectedInput = new RideRequest(passengerId, MockDefaultIT.generateCoordinateInfo(), MockDefaultIT.generateCoordinateInfo());
        when(requestRideUseCase.execute(any()))
                .thenThrow(new GatewayNotFoundError("Account not found! AccountId: " + passengerId));

        // When
        final var request = MockMvcRequestBuilders
                .post("/rides/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(expectedInput));

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)));
        verify(requestRideUseCase, times(1)).execute(any());
    }

    @Test
    void shouldGetRideWhenRideIsRequested() throws Exception {
        // Given
        final var rideId = MockDefaultIT.generateIdentifier();
        final var expectedRideId = responseRequested.rideId().toString();
        final var expectedPassengerId = responseRequested.passenger().accountId().toString();
        final var expectedStatus = "REQUESTED";


        when(getRideUseCase.execute(rideId))
                .thenReturn(responseRequested);

        // When
        final var request = MockMvcRequestBuilders
                .get("/rides/{rideId}", rideId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.ride_id", equalTo(expectedRideId)))
                .andExpect(jsonPath("$.passenger.account_id", equalTo(expectedPassengerId)))
                .andExpect(jsonPath("$.driver", equalTo(null)))
                .andExpect(jsonPath("$.status", equalTo(expectedStatus)))
                .andExpect(jsonPath("$.fare", equalTo(null)))
                .andExpect(jsonPath("$.distance", equalTo(null)));

        verify(getRideUseCase, times(1)).execute(rideId);
    }

    @Test
    void shouldGetRideWhenRideIsAccepted() throws Exception {
        // Given
        final var rideId = MockDefaultIT.generateIdentifier();
        final var expectedRideId = responseAccepted.rideId().toString();
        final var expectedPassengerId = responseAccepted.passenger().accountId().toString();
        final var expectedDriverId = responseAccepted.driver().accountId().toString();
        final var expectedStatus = "ACCEPTED";


        when(getRideUseCase.execute(rideId))
                .thenReturn(responseAccepted);
        // When
        final var request = MockMvcRequestBuilders
                .get("/rides/{rideId}", rideId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.ride_id", equalTo(expectedRideId)))
                .andExpect(jsonPath("$.passenger.account_id", equalTo(expectedPassengerId)))
                .andExpect(jsonPath("$.driver.account_id", equalTo(expectedDriverId)))
                .andExpect(jsonPath("$.status", equalTo(expectedStatus)))
                .andExpect(jsonPath("$.fare", equalTo(null)))
                .andExpect(jsonPath("$.distance", equalTo(null)));

        verify(getRideUseCase, times(1)).execute(rideId);
    }

    @Test
    void shouldAcceptRideWithSuccess() throws Exception {
        // Given && When
        final var request = MockMvcRequestBuilders
                .put("/rides/accept")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(MockDefaultIT.generateAcceptRideRequest()));

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk());
        verify(this.acceptRideUseCase, times(1)).execute(any());
    }

    @Test
    void shouldThrowExceptionAcceptRideWhenInvalidStatus() throws Exception {
        // When
        final var expectedMessage = "Invalid status!";
        doThrow(new InvalidArgumentError("Invalid status!"))
                .when(this.acceptRideUseCase).execute(any());

        // Given
        final var request = MockMvcRequestBuilders
                .put("/rides/accept")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(MockDefaultIT.generateAcceptRideRequest()));

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)));
        verify(this.acceptRideUseCase, times(1)).execute(any());
    }


    @Test
    void shouldStartRideWithSuccess() throws Exception {
        // When
        final var expectedRideId = UUID.randomUUID().toString();

        // Given
        final var request = MockMvcRequestBuilders
                .put("/rides/start/{rideId}", expectedRideId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk());
        verify(this.startRideUseCase, times(1)).execute(any());
    }

    @Test
    void shouldThrowExceptionStartRideWhenInvalidStatus() throws Exception {
        // When
        final var expectedRideId = UUID.randomUUID().toString();
        final var expectedMessage = "Invalid status!";
        doThrow(new InvalidArgumentError("Invalid status!"))
                .when(this.startRideUseCase).execute(any());

        // Given
        final var request = MockMvcRequestBuilders
                .put("/rides/start/{rideId}", expectedRideId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)));
        verify(this.startRideUseCase, times(1)).execute(any());
    }


    @Test
    void shouldFinishRideWithSuccess() throws Exception {
        // When
        final var expectedRideId = UUID.randomUUID().toString();

        // Given
        final var request = MockMvcRequestBuilders
                .put("/rides/finish/{rideId}", expectedRideId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isOk());
        verify(this.finishRideUseCase, times(1)).execute(any());
    }

    @Test
    void shouldThrowExceptionFinishRideWhenInvalidStatus() throws Exception {
        // When
        final var expectedRideId = UUID.randomUUID().toString();
        final var expectedMessage = "Invalid status!";
        doThrow(new InvalidArgumentError("Invalid status!"))
                .when(this.finishRideUseCase).execute(any());

        // Given
        final var request = MockMvcRequestBuilders
                .put("/rides/finish/{rideId}", expectedRideId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = super.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.log());

        // Then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)));
        verify(this.finishRideUseCase, times(1)).execute(any());
    }

}
