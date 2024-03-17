package api;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountOutput;
import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpInput;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import config.ControllerDefaultIT;
import mock.MockDefault;
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

class AccountControllerTest extends ControllerDefaultIT {

    private static Account account;
    private static Account accountDriver;

    @MockBean
    private SignUpUseCase signUpUseCase;
    @MockBean
    private GetAccountUseCase getAccountUseCase;
    @MockBean
    private QueueProducer queueProducer;

    @BeforeEach
    void beforeSetup() {
        account = MockDefault.getAccountPassenger();
        accountDriver = MockDefault.getAccountDriver();
    }

    @Test
    void shouldValidParametersCreateNewAccount() throws Exception {
        // Given
        final var expectedAccountId = "d312f737-20c6-4e2c-a261-e3b16ffa4b64";
        final var expectedInput = SignUpInput.with(account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());
        when(signUpUseCase.execute(any())).thenReturn(expectedAccountId);

        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(expectedInput));

        final var response = super.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // Then
        response
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.account_id", equalTo(expectedAccountId)));

        verify(signUpUseCase, times(1)).execute(any());
    }

    @Test
    void shouldInvalidParametersNotCreateNewAccount() throws Exception {
        // Given
        final var expectedError = "Invalid name!";
        final var expectedInput = SignUpInput.with(account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());
        when(signUpUseCase.execute(any()))
                .thenThrow(new InvalidArgumentError("Invalid name!"));

        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expectedInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // Then
        response
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", equalTo(expectedError)));

        verify(signUpUseCase, times(1)).execute(any());
    }

    @Test
    void shouldGetAccountPassengerWithSuccess() throws Exception {
        // Given
        final var expectedAccountId = UUID.fromString("d312f737-20c6-4e2c-a261-e3b16ffa4b64");
        final var expectedOutput = GetAccountOutput.from(account);
        when(getAccountUseCase.execute(expectedAccountId)).thenReturn(expectedOutput);

        // When
        final var request = MockMvcRequestBuilders
                .get("/accounts/get-account/{accountId}", expectedAccountId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = super.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_passenger", equalTo(account.isPassenger())))
                .andExpect(jsonPath("$.car_plate", equalTo(account.getCarPlate())))
                .andExpect(jsonPath("$.name", equalTo(account.getName())))
                .andExpect(jsonPath("$.email", equalTo(account.getEmail())))
                .andExpect(jsonPath("$.cpf", equalTo(account.getCpf())));

        verify(getAccountUseCase, times(1)).execute(expectedAccountId);
    }

    @Test
    void shouldGetAccountDriverWithSuccess() throws Exception {
        // Given
        final var expectedAccountId = UUID.fromString("d312f737-20c6-4e2c-a261-e3b16ffa4b64");
        final var expectedOutput = GetAccountOutput.from(accountDriver);
        when(getAccountUseCase.execute(expectedAccountId)).thenReturn(expectedOutput);

        // When
        final var request = MockMvcRequestBuilders
                .get("/accounts/get-account/{accountId}", expectedAccountId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = super.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_driver", equalTo(accountDriver.isDriver())))
                .andExpect(jsonPath("$.car_plate", equalTo(accountDriver.getCarPlate())))
                .andExpect(jsonPath("$.name", equalTo(accountDriver.getName())))
                .andExpect(jsonPath("$.email", equalTo(accountDriver.getEmail())))
                .andExpect(jsonPath("$.cpf", equalTo(accountDriver.getCpf())));

        verify(getAccountUseCase, times(1)).execute(expectedAccountId);
    }

    @Test
    void shouldSignUpAsyncWithSuccess() throws Exception {
        // Given
        final var expectedInput = SignUpInput.with(account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());
        doNothing().when(queueProducer).sendMessage(any(), any());

        // When
        final var request = MockMvcRequestBuilders
                .post("/accounts/signup-async")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapper.writeValueAsString(expectedInput));

        final var response = super.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // Then
        response
                .andExpect(status().isNoContent());

        verify(queueProducer, times(1)).sendMessage(any(), any());
    }
}

