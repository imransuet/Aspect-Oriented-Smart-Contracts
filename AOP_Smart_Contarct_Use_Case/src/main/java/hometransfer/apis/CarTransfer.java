package hometransfer.apis;

import hometransfer.DecoratorManager;
import hometransfer.decorators.CachingDecorator;
import hometransfer.decorators.LoggingDecorator;
import hometransfer.implementations.CarTransferBL;
import hometransfer.interfaces.CarTransferInterface;
import hometransfer.interfaces.HomeTransferInterface;
import hometransfer.models.Car;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

import java.nio.charset.StandardCharsets;

@Contract(
        name = "CarTransfer",
        info = @Info(
                title = "A Car Transfer contract",
                description = "A Demonstration of Chaincode about Car Transfer with dynamic aspects",
                version = "0.0.1-SNAPSHOT"))
public class CarTransfer implements ContractInterface {


    static  int checkcar=-1;
    DecoratorManager<CarTransferInterface> manager;
    CarTransferInterface carTransfer = new CarTransferBL();
    static CarTransferInterface decoratedChaincode;



    @Transaction()
    public void initLedger(final Context ctx) {
        checkcar++;
        CarTransferInterface targetChaincode = getTargetChaincode(ctx);

        System.out.println("i am in initLedger of Car Transfer "+checkcar);
        targetChaincode.initLedger(ctx);
    }


    @Transaction()
    public Car addNewCar(final Context ctx, final String id, final String brand, final String model, final String color, final String ownerName, final String price, final String license) {
        CarTransferInterface targetChaincode = getTargetChaincode(ctx);
        checkcar++;
        System.out.println("i am in addNewCar "+checkcar);
        return targetChaincode .addNewCar(ctx, id, brand, model,  color, ownerName,  price, license);
    }


    @Transaction()
    public Car queryCar(final Context ctx, final String id) {
        CarTransferInterface targetChaincode = getTargetChaincode(ctx);
        checkcar++;
        System.out.println("i am in queryCar "+checkcar);
        return targetChaincode .queryCar(ctx, id);
    }


    @Transaction()
    public Car changeCarOwnership(final Context ctx, final String id, final String newCarOwner) {
        CarTransferInterface targetChaincode = getTargetChaincode(ctx);
        checkcar++;
        System.out.println("i am in changeCarOwnership "+checkcar);
        return targetChaincode.changeCarOwnership(ctx, id, newCarOwner);
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

    private CarTransferInterface getTargetChaincode(final Context ctx) {
        if (shouldComposeNewObject(ctx)) {
            classDecorate();
            decoratedChaincode = manager.composeChaincodeBasedOnMetadata(ctx);
            return  decoratedChaincode;
        } else {
            return decoratedChaincode != null ? decoratedChaincode : carTransfer;
        }

    }
    public void classDecorate()
    {
        System.out.println("i entered classDecorate ");
        manager = new DecoratorManager<>(carTransfer);
        manager.addDecorator(new LoggingDecorator<>());
        manager.addDecorator(new CachingDecorator<>());
        System.out.println("i left classDecorate");
    }


}
