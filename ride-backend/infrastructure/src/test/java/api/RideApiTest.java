package api;

import com.dev.torhugo.clean.code.arch.application.getride.GetRideUseCase;
import com.dev.torhugo.clean.code.arch.application.requestride.RequestRideUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.RideController;
import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
