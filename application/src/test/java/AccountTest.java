import com.dev.torhugo.clean.code.arch.application.singup.CreateSingUpInput;
import com.dev.torhugo.clean.code.arch.application.singup.SignUpUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class AccountTest {

    @InjectMocks
    private SignUpUseCase useCase;

//    @Mock
//    private AccountGateway accountGateway;

    @Test
    void shouldSingUpPassengerSuccessfully(){
        // Given
        final var expectedName = "Test Test";
        final var expectedEmail = "test@example.com";
        final var expectedCpf = "648.808.745-23";
        final var expectedIsPassenger = true;
        final var expectedIsDriver = false;

        final var input = new CreateSingUpInput(expectedName, expectedEmail, expectedCpf, null, expectedIsPassenger, expectedIsDriver);
//        when(accountGateway.getByEmail(any())).thenAnswer(returnsFirstArg());
        // When
//        final var actualOutput = useCase.execute(input);

        // Then
//        assertNotNull(actualOutput);
//        verify(accountGateway, times(1)).save(argThat(account ->
//            nonNull(account.getAccountId())
//                    && Objects.equals(expectedName, account.getName())
//                    && Objects.equals(expectedEmail, account.getEmail())
//                    && Objects.equals(expectedCpf, account.getCpf())
//                    && Objects.equals(expectedIsPassenger, account.getPassenger())
//                    && Objects.equals(expectedIsDriver, account.getDriver())
//                    && Objects.nonNull(account.getCreatedAt())
//                    && Objects.isNull(account.getUpdatedAt())
//
//        ));
    }
}
