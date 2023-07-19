package hometransfer.contract;

import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

public class AccountContractTest {
    private AccountContract contract;
    private Context ctx;

    @BeforeEach
    public void setUp() {
        contract = mock(AccountContract.class);
        ctx = mock(Context.class);
    }

    @Nested
    class InvokeAddNewAccountTransaction {

        @Test
        public void whenAccountCanBeAdded() {
            String expectedAccountId = "1";

            when(contract.addNewAccount(ctx, expectedAccountId, "1", 5000))
                    .thenReturn(new Account(expectedAccountId, "1", 5000));

            Account account = contract.addNewAccount(ctx, expectedAccountId, "1", 5000);

            assertThat(account).isNotNull();
            verify(contract, times(1)).addNewAccount(ctx, expectedAccountId, "1", 5000);
        }
    }

    @Nested
    class InvokeQueryAccountTransaction {

        @Test
        public void whenAccountExists() {
            String expectedAccountId = "1";

            when(contract.queryAccount(ctx, expectedAccountId))
                    .thenReturn(new Account(expectedAccountId, "1", 5000));

            Account account = contract.queryAccount(ctx, expectedAccountId);

            assertThat(account).isNotNull();
            verify(contract, times(1)).queryAccount(ctx, expectedAccountId);
        }

        @Test
        public void whenAccountDoesNotExist() {
            String expectedAccountId = "2";

            when(contract.queryAccount(ctx, expectedAccountId))
                    .thenReturn(null);

            Throwable thrown = catchThrowable(() -> {
                contract.queryAccount(ctx, expectedAccountId);
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Account " + expectedAccountId + " does not exist");
        }
    }

    @Nested
    class InvokeTransferBalanceTransaction {

        @Test
        public void whenTransferIsSuccessful() {
            String senderAccountId = "1";
            String receiverAccountId = "2";
            double transferAmount = 1000;

            when(contract.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount))
                    .thenReturn(String.format("Successfully transferred %.2f balance from account %s to account %s", transferAmount, senderAccountId, receiverAccountId));

            String response = contract.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);

            assertThat(response).isNotNull();
            verify(contract, times(1)).transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
        }

        @Test
        public void whenTransferFailsDueToInsufficientBalance() {
            String senderAccountId = "1";
            String receiverAccountId = "2";
            double transferAmount = 10000;

            when(contract.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount))
                    .thenReturn(null);

            Throwable thrown = catchThrowable(() -> {
                contract.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Sender Account " + senderAccountId + " does not have enough balance");
        }
    }
}
