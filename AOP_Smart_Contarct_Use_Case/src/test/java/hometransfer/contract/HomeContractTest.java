package hometransfer.contract;

import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

public final class HomeContractTest {
    private HomeContract contract;
    private Context ctx;

    @BeforeEach
    public void setUp() {
        contract = mock(HomeContract.class);
        ctx = mock(Context.class);
    }

    @Nested
    class InvokeAddNewHomeTransaction {

        @Test
        public void whenHomeCanBeAdded() {
            String expectedHomeId = "1";

            when(contract.addNewHome(ctx, expectedHomeId, "1", "LakeView", "Dhaka",
                    "1000 sqm", "Type1", 500000.0, 1990))
                    .thenReturn(new Home(expectedHomeId, "1", "LakeView", "Dhaka",
                            "1000 sqm", "Type1", 500000.0, 1990));

            Home home = contract.addNewHome(ctx, expectedHomeId, "1", "LakeView", "Dhaka",
                    "1000 sqm", "Type1", 500000.0, 1990);

            assertThat(home).isNotNull();
            verify(contract, times(1)).addNewHome(ctx, expectedHomeId, "1", "LakeView", "Dhaka",
                    "1000 sqm", "Type1", 500000.0, 1990);
        }
    }

    @Nested
    class InvokeQueryHomeTransaction {

        @Test
        public void whenHomeExists() {
            String expectedHomeId = "1";

            when(contract.queryHome(ctx, expectedHomeId))
                    .thenReturn(new Home(expectedHomeId, "1", "LakeView", "Dhaka",
                            "1000 sqm", "Type1", 500000.0, 1990));

            Home home = contract.queryHome(ctx, expectedHomeId);

            assertThat(home).isNotNull();
            verify(contract, times(1)).queryHome(ctx, expectedHomeId);
        }

        @Test
        public void whenHomeDoesNotExist() {
            String expectedHomeId = "2";

            when(contract.queryHome(ctx, expectedHomeId))
                    .thenReturn(null);

            Throwable thrown = catchThrowable(() -> {
                contract.queryHome(ctx, expectedHomeId);
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Home " + expectedHomeId + " does not exist");
        }
    }

    @Nested
    class InvokeChangeHomeOwnershipTransaction {

        @Test
        public void whenHomeOwnershipCanBeChanged() {
            String expectedHomeId = "1";
            String newHomeOwner = "2";

            when(contract.changeHomeOwnership(ctx, expectedHomeId, newHomeOwner))
                    .thenReturn(new Home(expectedHomeId, newHomeOwner, "LakeView", "Dhaka",
                            "1000 sqm", "Type1", 500000.0, 1990));

            Home home = contract.changeHomeOwnership(ctx, expectedHomeId, newHomeOwner);

            assertThat(home).isNotNull();
            assertThat(home.getHomeOwnerId()).isEqualTo(newHomeOwner);
            verify(contract, times(1)).changeHomeOwnership(ctx, expectedHomeId, newHomeOwner);
        }
    }
}
