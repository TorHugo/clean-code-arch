package repository;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.domain.error.exception.RepositoryNotFoundError;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.AccountRepositoryImpl;
import config.AnnotationDefaultIT;
import mock.MockDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends AnnotationDefaultIT {

    @Autowired
    private AccountRepositoryImpl accountRepository;
    private static Account accountDriver;
    private static Account accountPassenger;
    @BeforeEach
    void beforeSetup() {
        accountDriver = MockDefault.getAccountDriver();
        accountPassenger = MockDefault.getAccountPassenger();
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldSaveAsNewAccountPassengerWithSuccess(){
        // Given && When
        this.accountRepository.save(accountPassenger);
        final var result = accountRepository.getByAccountId(accountPassenger.getAccountId());

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(accountPassenger.getName(), result.getName()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountPassenger.getAccountId(), result.getAccountId()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountPassenger.getEmail(), result.getEmail()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountPassenger.getCpf(), result.getCpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountPassenger.getCarPlate(), result.getCarPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isPassenger(), expectedTrue), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isDriver(), expectedFalse), expectedMessageEqual);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldSaveAsNewAccountDriverWithSuccess(){
        // Given && When
        this.accountRepository.save(accountDriver);
        final var result = accountRepository.getByAccountId(accountDriver.getAccountId());

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getAccountId(), result.getAccountId()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getName(), result.getName()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getEmail(), result.getEmail()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCpf(), result.getCpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(accountDriver.getCarPlate(), result.getCarPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isPassenger(), expectedFalse), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isDriver(), expectedTrue), expectedMessageEqual);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowExceptionWhenAccountNotFound(){
        // Given && When
        final var exception = assertThrows(RepositoryNotFoundError.class, () -> {
            this.accountRepository.getByAccountId(accountPassenger.getAccountId());
        });

        // Then
        assertNotNull(exception);
        assertEquals(expectedTrue, Objects.equals(exception.getMessage(), expectedMessageAccountNotFound), expectedMessageEqual);
    }

    @Test
    @Sql(scripts = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldIsNullAccountWhenNotSaveAccount(){
        // Given && When
        final var result = this.accountRepository.getByEmail(accountPassenger.getEmail());

        // Then
        assertNull(result);
    }
}
