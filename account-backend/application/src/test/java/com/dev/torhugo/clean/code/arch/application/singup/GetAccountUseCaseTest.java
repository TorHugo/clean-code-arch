package com.dev.torhugo.clean.code.arch.application.singup;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.application.repository.AccountRepository;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetAccountUseCaseTest {

    @Mock
    AccountRepository accountRepository;

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
        when(accountRepository.getByAccountId(expectedAccountId)).thenReturn(accountClone);
        // When
        final var actualAccount = getAccountUseCase.execute(expectedAccountId);
        // Then
        verify(accountRepository, times(1)).getByAccountId(any());
        assertEquals(expectedAccountId, actualAccount.accountId());
        assertEquals(expectedName, actualAccount.name());
        assertEquals(expectedEmail, actualAccount.email());
        assertEquals(expectedCpf, actualAccount.cpf());
        assertEquals(expectedIsPassenger, actualAccount.isPassenger());
        assertEquals(expectedIsDriver, actualAccount.isDriver());
        assertNotNull(actualAccount.createdAt());
        assertNull(actualAccount.updatedAt());
    }
}
