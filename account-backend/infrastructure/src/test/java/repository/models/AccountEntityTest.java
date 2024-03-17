package repository.models;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;
import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import config.AnnotationDefaultIT;
import mock.MockDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountEntityTest extends AnnotationDefaultIT {
    private static Account account;
    @BeforeEach
    void beforeSetup() {
        account = MockDefault.getAccountPassenger();
    }
    @Test
    void shouldInstantiateAccountEntityWhenValidAccount(){
        // Given && When
        final var result = AccountEntity.fromAggregate(account);

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(account.getName(), result.getName()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getAccountId(), result.getAccountId()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getEmail(), result.getEmail()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getCpf(), result.getCpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getCarPlate(), result.getCarPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isPassenger(), expectedTrue), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isDriver(), expectedFalse), expectedMessageEqual);
    }

    @Test
    void shouldRestoreAccountWhenValidAccountEntity(){
        // Given
        final var entity = new AccountEntity();
        entity.setAccountId(account.getAccountId());
        entity.setName(account.getName());
        entity.setEmail(account.getEmail());
        entity.setCpf(account.getCpf());
        entity.setDriver(account.isDriver());
        entity.setPassenger(account.isPassenger());
        entity.setCarPlate(account.getCarPlate());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setUpdatedAt(account.getUpdatedAt());

        // When
        final var result = AccountEntity.toAggregate(entity);

        // Then
        assertNotNull(result);
        assertEquals(expectedTrue, Objects.equals(account.getAccountId(), result.getAccountId()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getName(), result.getName()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getEmail(), result.getEmail()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getCpf(), result.getCpf()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getCarPlate(), result.getCarPlate()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isPassenger(), expectedTrue), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(result.isDriver(), expectedFalse), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getCreatedAt(), result.getCreatedAt()), expectedMessageEqual);
        assertEquals(expectedTrue, Objects.equals(account.getUpdatedAt(), result.getUpdatedAt()), expectedMessageEqual);

    }
}
