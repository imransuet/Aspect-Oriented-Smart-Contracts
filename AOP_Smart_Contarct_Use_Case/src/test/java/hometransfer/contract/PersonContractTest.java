package hometransfer.contract;

import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

public final class PersonContractTest {
    private PersonContract contract;
    private Context ctx;

    @BeforeEach
    public void setUp() {
        contract = mock(PersonContract.class);
        ctx = mock(Context.class);
    }

    @Nested
    class InvokeAddNewPersonTransaction {

        @Test
        public void whenPersonCanBeAdded() {
            String expectedPersonId = "1";

            when(contract.addNewPerson(ctx, expectedPersonId, "John", "100 Main St",
                    "1990-01-01", "john@example.com", "555-555-5555", "USA"))
                    .thenReturn(new Person(expectedPersonId, "John", "100 Main St",
                            "1990-01-01", "john@example.com", "555-555-5555", "USA"));

            Person person = contract.addNewPerson(ctx, expectedPersonId, "John", "100 Main St",
                    "1990-01-01", "john@example.com", "555-555-5555", "USA");

            assertThat(person).isNotNull();
            verify(contract, times(1)).addNewPerson(ctx, expectedPersonId, "John", "100 Main St",
                    "1990-01-01", "john@example.com", "555-555-5555", "USA");
        }
    }

    @Nested
    class InvokeQueryPersonTransaction {

        @Test
        public void whenPersonExists() {
            String expectedPersonId = "2";

            when(contract.queryPerson(ctx, expectedPersonId))
                    .thenReturn(new Person(expectedPersonId, "John", "100 Main St",
                            "1990-01-01", "john@example.com", "555-555-5555", "USA"));

            Person person = contract.queryPerson(ctx, expectedPersonId);

            assertThat(person).isNotNull();
            verify(contract, times(1)).queryPerson(ctx, expectedPersonId);
        }

        @Test
        public void whenPersonDoesNotExist() {
            String expectedPersonId = "1";

            when(contract.queryPerson(ctx, expectedPersonId))
                    .thenReturn(null);

            Throwable thrown = catchThrowable(() -> {
                contract.queryPerson(ctx, expectedPersonId);
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Person " + expectedPersonId + " does not exist");
        }
    }
}
