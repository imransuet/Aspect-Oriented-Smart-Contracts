package hometransfer.interfaces;

import hometransfer.models.Car;
import org.hyperledger.fabric.contract.Context;

public interface CarTransferInterface {
    void initLedger(final Context ctx);

    Car addNewCar(final Context ctx, final String id, final String brand, final String model, final String color,final String ownerName, final String price, final String license);

    Car queryCar(final Context ctx, final String id);

    Car changeCarOwnership(final Context ctx, final String id, final String newCarOwner);
}
