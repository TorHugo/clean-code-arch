package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.account.AccountGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignUpUseCaseTest {

    @Mock
    AccountGateway accountGateway;
    @InjectMocks
    SignUpUseCase signUpUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteSignUpPassengerWhenValidParams() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var expectedInput = new SingUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        when(accountGateway.getByEmail(anyString())).thenReturn(Optional.empty());

        // When
        final var actualAccount = signUpUseCase.execute(expectedInput);

        // Then
        assertNotNull(actualAccount);
        verify(accountGateway, times(1)).getByEmail(any());
        verify(accountGateway, times(1)).save(argThat(account ->
                Objects.nonNull(account.getAccountId())
                        && Objects.equals(expectedName, account.getName())
                        && Objects.equals(expectedEmail, account.getEmail())
                        && Objects.equals(expectedCpf, account.getCpf())
                        && Objects.equals(expectedIsPassenger, account.isPassenger())
                        && Objects.equals(expectedIsDriver, account.isDriver())
                        && Objects.nonNull(account.getCreatedAt())
                        && Objects.isNull(account.getUpdatedAt())
        ));
    }

    @Test
    void shouldExecuteSignUpDriverWhenValidParams() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = false;
        final var expectedIsDriver = true;

        final var expectedInput = new SingUpInput(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        when(accountGateway.getByEmail(anyString())).thenReturn(Optional.empty());

        // When
        final var actualAccount = signUpUseCase.execute(expectedInput);

        // Then
        assertNotNull(actualAccount);
        verify(accountGateway, times(1)).getByEmail(any());
        verify(accountGateway, times(1)).save(argThat(account ->
                Objects.nonNull(account.getAccountId())
                        && Objects.equals(expectedName, account.getName())
                        && Objects.equals(expectedEmail, account.getEmail())
                        && Objects.equals(expectedCpf, account.getCpf())
                        && Objects.equals(expectedCarPlate, account.getCarPlate())
                        && Objects.equals(expectedIsPassenger, account.isPassenger())
                        && Objects.equals(expectedIsDriver, account.isDriver())
                        && Objects.nonNull(account.getCreatedAt())
                        && Objects.isNull(account.getUpdatedAt())
        ));
    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        // Given
        final var expectedError = "Account already exists!";

        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var expectedInput = new SingUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        final var accountAlreadyExists = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        when(accountGateway.getByEmail(anyString())).thenReturn(Optional.of(accountAlreadyExists));

        // When
        final var exception = assertThrows(IllegalArgumentException.class, () ->
                signUpUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountGateway, times(1)).getByEmail(any());
        verify(accountGateway, times(0)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenInvalidParameters() {
        // Given
        final var expectedError = "Invalid cpf!";

        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "123.123.123-12";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var expectedInput = new SingUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        when(accountGateway.getByEmail(anyString())).thenReturn(Optional.empty());

        // When
        final var exception = assertThrows(IllegalArgumentException.class, () ->
                signUpUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountGateway, times(1)).getByEmail(any());
        verify(accountGateway, times(0)).save(any());
    }
}