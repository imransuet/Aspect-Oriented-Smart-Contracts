package hometransfer.apis;

import hometransfer.DecoratorManager;
import hometransfer.decorators.CachingDecorator;
import hometransfer.decorators.LoggingDecorator;
import hometransfer.implementations.CarTransferBL;
import hometransfer.interfaces.CarTransferInterface;
import hometransfer.models.Car;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "CarTransfer",
        info = @Info(
                title = "A Car Transfer contract",
                description = "A Demonstration of Chaincode about Car Transfer with dynamic aspects",
                version = "0.0.1-SNAPSHOT"))
public class CarTransfer implements ContractInterface {


    CarTransferBL chaincode;
    DecoratorManager<CarTransferInterface> manager;
    CarTransferInterface decoratedChaincode;


    @Transaction()
    public void initLedger(final Context ctx) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        System.out.println("i am in initLedger ");
        decoratedChaincode .initLedger(ctx);
    }


    @Transaction()
    public Car addNewCar(final Context ctx, final String id, final String brand, final String model, final String color, final String ownerName, final String price, final String license) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .addNewCar(ctx, id, brand, model,  color, ownerName,  price, license);
    }


    @Transaction()
    public Car queryCar(final Context ctx, final String id) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .queryCar(ctx, id);
    }


    @Transaction()
    public Car changeCarOwnership(final Context ctx, final String id, final String newCarOwner) {
        classDecorate();
        decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
        return decoratedChaincode .changeCarOwnership(ctx, id, newCarOwner);
    }
    public void classDecorate()
    {
        System.out.println("i entered classDecorate ");
        CarTransferInterface carTransfer = new CarTransferBL();
        manager = new DecoratorManager<>(carTransfer);
        manager.addDecorator(new LoggingDecorator<>());
        manager.addDecorator(new CachingDecorator<>());
        System.out.println("i left classDecorate");
    }


}
