package account;

import com.dev.torhugo.clean.code.arch.domain.account.Account;
import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.dev.torhugo.clean.code.arch.domain.utils.IdentifierUtils.generateIdentifier;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldInstantiateAccountIsPassengerWhenValidParams() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When
        final var actualAccount = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);

        // Then
        assertNotNull(actualAccount.getAccountId());
        assertEquals(expectedName, actualAccount.getName());
        assertEquals(expectedEmail, actualAccount.getEmail());
        assertEquals(expectedCpf, actualAccount.getCpf());
        assertNull(actualAccount.getCarPlate());
        assertEquals(expectedIsPassenger, actualAccount.isPassenger());
        assertEquals(expectedIsDriver, actualAccount.isDriver());
        assertNotNull(actualAccount.getCreatedAt());
        assertNull(actualAccount.getUpdatedAt());
    }

    @Test
    void shouldInstantiateAccountIsDriverWhenValidParams() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = false;
        final var expectedIsDriver = true;

        // When
        final var actualAccount = Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);

        // Then
        assertNotNull(actualAccount.getAccountId());
        assertEquals(expectedName, actualAccount.getName());
        assertEquals(expectedEmail, actualAccount.getEmail());
        assertEquals(expectedCpf, actualAccount.getCpf());
        assertEquals(expectedCarPlate, actualAccount.getCarPlate());
        assertEquals(expectedIsPassenger, actualAccount.isPassenger());
        assertEquals(expectedIsDriver, actualAccount.isDriver());
        assertNotNull(actualAccount.getCreatedAt());
        assertNull(actualAccount.getUpdatedAt());
    }

    @Test
    void shouldRestoreAccountWithSuccess() {
        // Given
        final var expectedAccountId = generateIdentifier();
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "ABC1234";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;
        final var expectedCreatedAt = LocalDateTime.now();

        // When
        final var actualAccount = Account.restore(expectedAccountId, expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate, expectedCreatedAt, null);

        // Then
        assertEquals(expectedAccountId, actualAccount.getAccountId());
        assertEquals(expectedName, actualAccount.getName());
        assertEquals(expectedEmail, actualAccount.getEmail());
        assertEquals(expectedCpf, actualAccount.getCpf());
        assertEquals(expectedCarPlate, actualAccount.getCarPlate());
        assertEquals(expectedIsPassenger, actualAccount.isPassenger());
        assertEquals(expectedIsDriver, actualAccount.isDriver());
        assertEquals(expectedCreatedAt, actualAccount.getCreatedAt());
        assertNull(actualAccount.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenInvalidName() {
        // Given
        final var expectedName = "Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When and Then
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        });

        // Assert
        assertEquals("Invalid name!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidEmail() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test_example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        });

        // Then
        assertEquals("Invalid email!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidCpf() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "123.123.123-12";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        });

        // Assert
        assertEquals("Invalid cpf!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCpfIsEmpty() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        });

        // Assert
        assertEquals("Invalid cpf!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCpfIsMaxDigits() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-233";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        // When
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, null);
        });

        // Assert
        assertEquals("Invalid cpf!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidCarPlate() {
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedCarPlate = "Invalid CarPlate.";
        final var expectedIsPassenger = false;
        final var expectedIsDriver = true;

        // When
        InvalidArgumentError exception = assertThrows(InvalidArgumentError.class, () -> {
            Account.create(expectedName, expectedEmail, expectedCpf, expectedIsPassenger, expectedIsDriver, expectedCarPlate);
        });

        // Then
        assertEquals("Invalid car plate!", exception.getMessage());
    }
}
