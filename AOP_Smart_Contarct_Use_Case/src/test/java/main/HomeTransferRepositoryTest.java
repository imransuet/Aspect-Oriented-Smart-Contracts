package main;

import hometransfer.Home;
import hometransfer.HomeTransferBL;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HomeTransferRepositoryTest {


    @Nested
    class HomeTransactionTest {

        @Test
        public void whenAgreementExists() {
            HomeTransferBL homeTransfer= new HomeTransferBL();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("ARG000"))
                    .thenReturn("{\"id\":\"1\",\"name\":\"Hillside\",\"area\":\"3000\",\"owner\":\"Elon Musk \",\"value\":\"50000\"}");



            Home home = homeTransfer.queryHomeById(ctx, "1");

            assertThat(home.getName())
                    .isEqualTo("Hillside");
            assertThat(home.getArea())
                    .isEqualTo("3000");
            assertThat(home.getOwner())
                    .isEqualTo("Elon Musk");
        }



    }



}
