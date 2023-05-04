package hometransfer.implementations;
import com.owlike.genson.Genson;
import hometransfer.interfaces.CarTransferInterface;
import hometransfer.models.Car;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class CarTransferBL implements CarTransferInterface {
    private final Genson genson = new Genson(); //used for serializing or deserializing the message in JSON and vice versa.
    private enum CarTransferErrors {
        CAR_NOT_FOUND,
        CAR_ALREADY_EXISTS
    }

    @Override
    public void initLedger(Context ctx) {

        ChaincodeStub stub= ctx.getStub();

        Car car = new Car("1","BMW","BMW iX xDrive40","Blue","Saif", "DAAAA000","95000");
        System.out.println("Initialization successful");

        String carState = genson.serialize(car);

        stub.putStringState("1", carState); //add to ledger

    }

    @Override
    public Car addNewCar(final Context ctx, final  String id, final  String brand, final  String model,final String color, final  String ownerName, final  String price, final  String license) {
        ChaincodeStub stub= ctx.getStub();
        String carState = stub.getStringState(id); //read from ledger
        if (!carState.isEmpty()) {
            String errorMessage = String.format("Car %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CarTransferBL.CarTransferErrors.CAR_ALREADY_EXISTS.toString());
        }
        Car car  = new Car(id, brand,model,color, ownerName, price, license);
        carState = genson.serialize(car); // serialization of java object to JSON format
        stub.putStringState(id, carState);
        return car;
    }


    @Override
    public Car queryCar(Context ctx, String id) {
        ChaincodeStub stub= ctx.getStub();
        String carState = stub.getStringState(id); //Query from ledger

        if (carState.isEmpty()) {
            String errorMessage = String.format("Car %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CarTransferBL.CarTransferErrors.CAR_NOT_FOUND.toString());
        }

        Car car = genson.deserialize(carState, Car.class); //deserializing JSON to java Object
        return car;
    }

    @Override
    public Car changeCarOwnership(Context ctx, String id, String newCarOwner) {
        ChaincodeStub stub= ctx.getStub();
        String carState = stub.getStringState(id);

        if (carState.isEmpty()) {
            String errorMessage = String.format("Car %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CarTransferBL.CarTransferErrors.CAR_NOT_FOUND.toString());
        }

        Car car= genson.deserialize(carState, Car.class);

        Car newCar= new Car(car.getId(),car.getBrand(),car.getModel(), car.getColor(),newCarOwner,car.getPrice(),car.getLicense());

        String newCarState = genson.serialize(newCar);

        stub.putStringState(id, newCarState);

        return newCar;
    }
}
