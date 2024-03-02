package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.gateway.AccountGateway;
import com.dev.torhugo.clean.code.arch.domain.error.exception.DatabaseNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetAccountUseCaseTest {

    @Mock
    AccountGateway accountGateway;

    @InjectMocks
    GetAccountUseCase getAccountUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteGetAccountPassengerWhenValidAccountId(){
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;
        final var expectedCarPlate = "ABC1234";

        final var accountClone = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        final var expectedAccountId = accountClone.getAccountId();
        when(accountGateway.getByAccountId(expectedAccountId)).thenReturn(accountClone);
        // When
        final var actualAccount = getAccountUseCase.execute(expectedAccountId);
        // Then
        verify(accountGateway, times(1)).getByAccountId(any());
        assertEquals(expectedAccountId, actualAccount.accountId());
        assertEquals(expectedName, actualAccount.name());
        assertEquals(expectedEmail, actualAccount.email());
        assertEquals(expectedCpf, actualAccount.cpf());
        assertEquals(expectedIsPassenger, actualAccount.isPassenger());
        assertEquals(expectedIsDriver, actualAccount.isDriver());
        assertNotNull(actualAccount.createdAt());
        assertNull(actualAccount.updatedAt());
    }

    @Test
    void shouldThrowExceptionWhenInvalidAccount(){
        // Given
        final var expectedException = "Account not found!";
        when(accountGateway.getByAccountId(any())).thenReturn(null);
        // When
        final var exception = assertThrows(DatabaseNotFoundError.class, () ->
                getAccountUseCase.execute(any()));
        // Then
        verify(accountGateway, times(1)).getByAccountId(any());
        assertEquals(expectedException, exception.getMessage());
    }
}
