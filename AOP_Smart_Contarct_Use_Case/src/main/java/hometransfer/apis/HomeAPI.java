package hometransfer.apis;

import hometransfer.DecoratorManager;
import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "HomeAPI",
        info = @Info(
                title = "HomeTransfer contract",
                description = "A Demonstration of Chaincode about Home Ownership Transfer with Logging Features",
                version = "0.0.1-SNAPSHOT"))



public final class HomeAPI implements ContractInterface{
    DecoratorManager decoratorManager = DecoratorManager.getInstance();
    HomeInterface decoratedChaincode;


    @Transaction()
    public void initLedger(final Context ctx) {

        System.out.println("HomeAPI: i am in initLedger");
        decoratedChaincode= decoratorManager.getHomeContract(ctx);
        decoratedChaincode .initLedger(ctx);
    }


    @Transaction()
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId, final String homeName, final String homeAddress, final String area) {
        decoratedChaincode= decoratorManager.getHomeContract(ctx);

        return decoratedChaincode .addNewHome(ctx, homeId, homeOwnerId, homeName, homeAddress, area);
    }


    @Transaction()
    public Home queryHome(final Context ctx, final String  homeId) {

        decoratedChaincode= decoratorManager.getHomeContract(ctx);
        return decoratedChaincode .queryHome(ctx, homeId);
    }


    @Transaction()
    public Home changeHomeOwnership(final Context ctx, final String  homeId, final String newHomeOwnerId) {

        decoratedChaincode= decoratorManager.getHomeContract(ctx);
        return decoratedChaincode .changeHomeOwnership(ctx, homeId, newHomeOwnerId);
    }




}
