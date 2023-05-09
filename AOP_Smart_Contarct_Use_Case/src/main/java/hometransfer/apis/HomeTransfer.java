package hometransfer.apis;
import hometransfer.DecoratorManager;
import hometransfer.implementations.HomeTransferBL;
import hometransfer.decorators.CachingDecorator;
import hometransfer.decorators.LoggingDecorator;
import hometransfer.interfaces.HomeTransferInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

import java.nio.charset.StandardCharsets;

@Contract(
        name = "HomeTransfer",
        info = @Info(
                title = "Home Transfer contract",
                description = "A Demonstration of Chaincode about Home Ownership Transfer with dynamic Aspects",
                version = "0.0.1-SNAPSHOT"))


@Default
public final class HomeTransfer implements ContractInterface{


    static int check=-5;
    HomeTransferInterface homeTransfer = new HomeTransferBL();
    DecoratorManager<HomeTransferInterface> manager;
    static HomeTransferInterface decoratedChaincode;


    @Transaction()
    public void initLedger(final Context ctx) {
        check++;
        HomeTransferInterface targetChaincode = getTargetChaincode(ctx);
        System.out.println("i am in initLedger ");

        targetChaincode.initLedger(ctx);
    }


    @Transaction()
    public Home addNewHome(final Context ctx, final String id, final String name, final String area, final String ownername, final String value) {
        check++;
        HomeTransferInterface targetChaincode = getTargetChaincode(ctx);

        return targetChaincode.addNewHome(ctx, id, name, area, ownername, value);
    }


    @Transaction()
    public Home queryHome(final Context ctx, final String id) {
        check++;
        System.out.println(check);
        System.out.println("Before checking shouldComposeNewObject");
        HomeTransferInterface targetChaincode = getTargetChaincode(ctx);
        System.out.println("After checking shouldComposeNewObject");

        return targetChaincode.queryHome(ctx, id);
    }


    @Transaction()
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwner) {
        HomeTransferInterface targetChaincode = getTargetChaincode(ctx);
        return targetChaincode.changeHomeOwnership(ctx, id, newHomeOwner);
    }

    public boolean shouldComposeNewObject(Context ctx) {
        byte[] transientValueBytes = ctx.getStub().getTransient().get("decorate");

        if (transientValueBytes != null) {
            String transientValue = new String(transientValueBytes, StandardCharsets.UTF_8);

            System.out.println(transientValue);
            if (transientValue.equals("true")) {
                return true;
            }
        }
        System.out.println("I am returning false because of empty transient value");
        return false;
    }
    private HomeTransferInterface getTargetChaincode(final Context ctx) {
        if (shouldComposeNewObject(ctx)) {
            classDecorate();
            decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
            return  decoratedChaincode;
        } else {
            return decoratedChaincode != null ? decoratedChaincode : homeTransfer;
        }

    }

    public void classDecorate()
    {
        System.out.println("i entered classDecorate ");
        manager = new DecoratorManager<>(homeTransfer);

        manager.addDecorator(new LoggingDecorator<>());
        manager.addDecorator(new CachingDecorator<>());
        System.out.println("i left classDecorate");
    }
}

