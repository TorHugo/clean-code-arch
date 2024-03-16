package api;

import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SingUpInput;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import config.ControllerDefaultIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerIT extends ControllerDefaultIT {

    @MockBean
    private SignUpUseCase signUpUseCase;

    @Test
    void shouldValidParametersCreateNewAccount() throws Exception {
        // Given
        final var expectedAccountId = "d312f737-20c6-4e2c-a261-e3b16ffa4b64";
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;
        final var expectedInput = SingUpInput.with(expectedName, expectedEmail, expectedCpf, "", expectedIsPassenger, expectedIsDriver);
        when(signUpUseCase.execute(any()))
                .thenReturn(expectedAccountId);

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
    }

    @Test
    void shouldInvalidParametersNotCreateNewAccount() throws Exception {
        // Given
        final var expectedError = "Invalid name!";

        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;
        final var expectedInput = SingUpInput.with(expectedName, expectedEmail, expectedCpf, "", expectedIsPassenger, expectedIsDriver);
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
    }

}

