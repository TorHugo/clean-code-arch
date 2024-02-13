package infrastructure;

import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.RideController;
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

import java.util.Random;
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

        final var input = new RequestRideInput(expectedPassengerId, CoordinatesRequestInfo.with(expectedFromLat, expectedFromLong), CoordinatesRequestInfo.with(expectedToLat, expectedToLong));
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
}
