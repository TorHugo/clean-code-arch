package infrastructure;

import annotation.ControllerTest;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import com.dev.torhugo.clean.code.arch.infrastructure.http.AccountAPI;
import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.AccountController;
import com.dev.torhugo.clean.code.arch.infrastructure.http.controller.models.SingUpRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = {AccountController.class, WebServerConfig.class})
@AutoConfigureMockMvc
class AccountApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpUseCase signUpUseCase;

    private final Gson gson = new Gson();

    @Test
    void shouldExecuteSignUpPassengerWhenValidParams() throws Exception {
        // Given
        final var expectedAccountId = "12345";
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var accountInput = new SingUpRequest(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);

        when(signUpUseCase.execute(any())).thenReturn(expectedAccountId);

        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(accountInput));

        final var response = mockMvc.perform(request);

        // Then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(expectedAccountId));
    }
}

