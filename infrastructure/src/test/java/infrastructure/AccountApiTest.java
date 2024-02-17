package infrastructure;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountOutput;
import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import com.dev.torhugo.clean.code.arch.infrastructure.configuration.WebServerConfig;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.AccountController;
import com.dev.torhugo.clean.code.arch.infrastructure.api.controller.models.SingUpRequest;
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

    @MockBean
    private GetAccountUseCase getAccountUseCase;

    private final Gson gson = new Gson();

    @Test
    void shouldExecuteSignUpPassengerWhenValidParams() throws Exception {
        // Given
        final var expectedName = "Test Passenger";
        final var expectedEmail = "test_passenger@example.com";
        final var expectedCpf = "648.808.745-23";
        final String expectedCarPlate = null;
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var accountInput = new SingUpRequest(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        final var accountObject = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        when(signUpUseCase.execute(any())).thenReturn(accountObject.getAccountId().toString());
        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(accountInput));

        final var response = mockMvc.perform(request);

        // Then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(accountObject.getAccountId().toString()));
    }

    @Test
    void shouldExceptionWhenInvalidName() throws Exception {
        // Given
        final var expectedError = "Invalid name!";
        final var expectedName = "Test Test";
        final var expectedEmail = "test_passenger@example.com";
        final var expectedCpf = "648.808.745-23";
        final String expectedCarPlate = null;
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var accountInput = new SingUpRequest(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        final var accountObject = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        when(signUpUseCase.execute(any())).thenThrow(new InvalidArgumentError(expectedError));
        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(accountInput));

        final var response = mockMvc.perform(request);

        // Then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedError));
    }

    @Test
    void shouldExecuteSignUpDriverWhenValidParams() throws Exception {
        // Given
        final var expectedName = "Test Driver";
        final var expectedEmail = "test_driver@example.com";
        final var expectedCpf = "648.808.745-23";
        final String expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = false;
        final var expectedIsDriver = true;

        final var accountInput = new SingUpRequest(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        final var accountObject = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        when(signUpUseCase.execute(any())).thenReturn(accountObject.getAccountId().toString());
        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(accountInput));

        final var response = mockMvc.perform(request);

        // Then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(accountObject.getAccountId().toString()));
    }

    @Test
    void shouldExecuteGetAccountPassengerWhenIsValidAccount() throws Exception {
        // Given
        final var expectedName = "Test Account";
        final var expectedEmail = "test_account@example.com";
        final var expectedCpf = "648.808.745-23";
        final String expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var accountObject = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        final var expectedAccountId = accountObject.getAccountId();

        when(getAccountUseCase.execute(any())).thenReturn(GetAccountOutput.from(accountObject));
        // When
        final var request = MockMvcRequestBuilders
                .get("/accounts/get-account/{accountId}", expectedAccountId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        final var response = mockMvc.perform(request);

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(expectedAccountId.toString()))
                .andExpect(jsonPath("$.name").value(expectedName))
                .andExpect(jsonPath("$.email").value(expectedEmail))
                .andExpect(jsonPath("$.cpf").value(expectedCpf))
                .andExpect(jsonPath("$.is_passenger").value(expectedIsPassenger))
                .andExpect(jsonPath("$.created_at").isNotEmpty());
    }

    
}

