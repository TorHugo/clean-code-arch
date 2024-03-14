package infrastructure;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideOutput;
import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.CoordinatesRequestInfo;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideInput;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Ride;
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

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {RideController.class, WebServerConfig.class})
@AutoConfigureMockMvc
class RideApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestRideUseCase requestRideUseCase;

    @MockBean
    private GetRideUseCase getRideUseCase;

    private final Gson gson = new Gson();
}
