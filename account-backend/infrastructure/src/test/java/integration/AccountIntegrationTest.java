package integration;

import com.dev.torhugo.clean.code.arch.application.getaccount.GetAccountUseCase;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpInput;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.AccountJpaRepository;
import config.AnnotationDefaultIT;
import mock.MockDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountIntegrationTest extends AnnotationDefaultIT {

    @Autowired
    private SignUpUseCase signUpUseCase;
    @Autowired
    private GetAccountUseCase getAccountUseCase;
    @Autowired
    private AccountJpaRepository accountRepository;

    private static Account accountPassenger;
    private static Account accountDriver;

    @BeforeEach
    void beforeSetup() {
        accountDriver = MockDefault.getAccountDriver();
        accountPassenger = MockDefault.getAccountPassenger();
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldCreateAsNewAccountPassengerWhenValidParams() {
        // Given
        assertEquals(0, accountRepository.count());
        final var expectedInput = SignUpInput.with(
                accountPassenger.getName(),
                accountPassenger.getEmail(),
                accountPassenger.getCpf(),
                accountPassenger.getCarPlate(),
                accountPassenger.isPassenger(),
                accountPassenger.isDriver()
        );

        // When
        final var accountId = this.signUpUseCase.execute(expectedInput);
        assertNotNull(accountId);
        final var actualAccount = this.getAccountUseCase.execute(UUID.fromString(accountId));
        assertNotNull(actualAccount);

        // Then
        assertEquals(expectedTrue, Objects.equals(actualAccount.accountId(), UUID.fromString(accountId)), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getName(), actualAccount.name()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getEmail(), actualAccount.email()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCpf(), actualAccount.cpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCarPlate(), actualAccount.carPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(actualAccount.isPassenger(), expectedTrue), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(actualAccount.isDriver(), expectedFalse), expectedMessageEqual);
        assertNotNull(actualAccount.createdAt());
        assertNull(actualAccount.updatedAt());
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldCreateAsNewAccountDriverWhenValidParams() {
        // Given
        assertEquals(0, accountRepository.count());
        final var expectedInput = SignUpInput.with(
                accountDriver.getName(),
                accountDriver.getEmail(),
                accountDriver.getCpf(),
                accountDriver.getCarPlate(),
                accountDriver.isPassenger(),
                accountDriver.isDriver()
        );

        // When
        final var accountId = this.signUpUseCase.execute(expectedInput);
        assertNotNull(accountId);
        final var actualAccount = this.getAccountUseCase.execute(UUID.fromString(accountId));
        assertNotNull(actualAccount);

        // Then
        assertEquals(expectedTrue, Objects.equals(actualAccount.accountId(), UUID.fromString(accountId)), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getName(), actualAccount.name()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getEmail(), actualAccount.email()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCpf(), actualAccount.cpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCarPlate(), actualAccount.carPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(actualAccount.isPassenger(), expectedFalse), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(actualAccount.isDriver(), expectedTrue), expectedMessageEqual);
        assertNotNull(actualAccount.createdAt());
        assertNull(actualAccount.updatedAt());
    }
}
