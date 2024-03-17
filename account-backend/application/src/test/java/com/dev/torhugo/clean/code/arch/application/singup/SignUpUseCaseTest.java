package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.messaging.QueueProducer;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.enums.MessageEnum;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignUpUseCaseTest {
    @InjectMocks
    SignUpUseCase signUpUseCase;
    @Mock
    AccountRepository accountRepository;
    @Mock
    QueueProducer queueProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteSignUpPassengerWhenValidParams() {
        // Given
        final var expectedQueue = "queue_test";
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var expectedInput = new SignUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        final var expectedWelcomeInput = SignUpMail.with(expectedEmail, MessageEnum.WELCOME.getMessage());
        when(accountRepository.getByEmail(anyString())).thenReturn(null);
        doNothing().when(queueProducer).sendMessage(expectedQueue, expectedWelcomeInput);

        // When
        final var actualAccount = signUpUseCase.execute(expectedInput);

        // Then
        assertNotNull(actualAccount);
        verify(accountRepository, times(1)).getByEmail(any());
        verify(accountRepository, times(1)).save(argThat(account ->
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
        final var expectedQueue = "queue_test";
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = false;
        final var expectedIsDriver = true;

        final var expectedInput = new SignUpInput(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        final var expectedWelcomeInput = SignUpMail.with(expectedEmail, MessageEnum.WELCOME.getMessage());
        when(accountRepository.getByEmail(anyString())).thenReturn(null);
        doNothing().when(queueProducer).sendMessage(expectedQueue, expectedWelcomeInput);
        // When
        final var actualAccount = signUpUseCase.execute(expectedInput);

        // Then
        assertNotNull(actualAccount);
        verify(accountRepository, times(1)).getByEmail(any());
        verify(accountRepository, times(1)).save(argThat(account ->
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

        final var expectedInput = new SignUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        final var accountAlreadyExists = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        when(accountRepository.getByEmail(anyString())).thenReturn(accountAlreadyExists);

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                signUpUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountRepository, times(1)).getByEmail(any());
        verify(accountRepository, times(0)).save(any());
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

        final var expectedInput = SignUpInput.with(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
        when(accountRepository.getByEmail(anyString())).thenReturn(null);

        // When
        final var exception = assertThrows(InvalidArgumentError.class, () ->
                signUpUseCase.execute(expectedInput));

        // Then
        assertEquals(expectedError, exception.getMessage());
        verify(accountRepository, times(1)).getByEmail(any());
        verify(accountRepository, times(0)).save(any());
    }
}