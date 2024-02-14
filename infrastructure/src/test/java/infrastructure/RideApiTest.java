package infrastructure;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.ride.Ride;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.RideController;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.GetRideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.RideResponse;
import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {RideController.class, WebServerConfig.class})
@AutoConfigureMockMvc
public class RideApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestRideUseCase requestRideUseCase;

    @MockBean
    private GetRideUseCase getRideUseCase;

    private final Gson gson = new Gson();

    @Test
    void shouldExecuteRequestRideWithSuccess() throws Exception {
        // Given
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedPassengerId = expectedAccount.getAccountId();
        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.from(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.from(expectedToLat, expectedToLong));
        final var output = RideResponse.from(UUID.randomUUID().toString());
        when(requestRideUseCase.execute(input)).thenReturn(output.rideId());

        // When
        final var request = MockMvcRequestBuilders
                .post("/rides/requested")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(input));

        final var response = mockMvc.perform(request);

        // Then
        response.andExpect(status().isCreated());
    }

    @Test
    void shouldExecuteGetRideWithSuccess() throws Exception {
        // Given
        final var nameAccount = "Test Test";
        final var emailAccount = "test@example.com";
        final var cpfAccount = "648.808.745-23";
        final var isPassenger = true;
        final var isDriver = false;
        final var expectedAccount = Account.create(nameAccount, emailAccount, cpfAccount, isPassenger, isDriver, null);

        final var expectedFromLat = Math.random();
        final var expectedFromLong = Math.random();
        final var expectedToLat = Math.random();
        final var expectedToLong = Math.random();
        final var expectedRide = Ride.create(expectedAccount.getAccountId(), expectedFromLat, expectedFromLong, expectedToLat, expectedToLong);

        final var expectedOutput = GetRideOutput.from(expectedRide, expectedAccount);

        when(this.getRideUseCase.execute(UUID.randomUUID())).thenReturn(expectedOutput);

        // When
        final var request = MockMvcRequestBuilders
                .get("/rides/{ride_id}", expectedRide.getRideId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        // TODO: refactor this test.
        // final var response = mockMvc.perform(request);

        // Then
        // response.andExpect(status().isOk());
    }
}
