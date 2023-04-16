package hometransfer;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "HomeTransfer",
        info = @Info(
                title = "HomeTransfer contract",
                description = "A Demonstration of Chaincode about Home Ownership Transfer with Logging Features",
                version = "0.0.1-SNAPSHOT"))


@Default
public final class HomeTransfer implements ContractInterface{
    HomeTransferBL chaincode;
    DecoratorManager manager;
    HomeTransferInterface decoratedChaincode;


    @Transaction()
    public void initLedger(final Context ctx) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        System.out.println("i am in initLedger ");
        decoratedChaincode .initLedger(ctx);
    }


    @Transaction()
    public Home addNewHome(final Context ctx, final String id, final String name, final String area, final String ownername, final String value) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .addNewHome(ctx, id, name, area, ownername, value);
    }


    @Transaction()
    public Home queryHomeById(final Context ctx, final String id) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .queryHome(ctx, id);
    }


    @Transaction()
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwner) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .changeHomeOwnership(ctx, id, newHomeOwner);
    }

    public void classDecorate()
    {
        System.out.println("i entered classDecorate ");
        chaincode = new HomeTransferBL();
        manager = new DecoratorManager(chaincode);

        //add decorators to manager repo
        manager.addDecorator(new LoggingDecorator());
        System.out.println("i left classDecorate");
    }
}

