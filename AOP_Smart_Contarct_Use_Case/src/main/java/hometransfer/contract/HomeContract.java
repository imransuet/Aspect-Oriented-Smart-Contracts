package hometransfer.contract;

import com.owlike.genson.Genson;
import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

public final class HomeContract implements HomeInterface {

    private final Genson genson = new Genson();
    private enum HomeTransferErrors {
        HOME_NOT_FOUND,
        HOME_ALREADY_EXISTS
    }

    public void initLedger(final Context ctx) {
        ChaincodeStub stub= ctx.getStub();
        Home home = new Home("1", "1", "lakeView", "Dhaka", "1000 sqm", "Type1", 500000.0, 1990);
        System.out.println("Initialization successful");

        String homeState = genson.serialize(home);
        stub.putStringState("1", homeState);
    }

    public Home addNewHome(final Context ctx, final String homeId, final String homeOwner,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        ChaincodeStub stub= ctx.getStub();
        String homeState = stub.getStringState(homeId);
        if (!homeState.isEmpty()) {
            String errorMessage = String.format("Home %s already exists", homeId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, HomeTransferErrors.HOME_ALREADY_EXISTS.toString());
        }
        Home home = new Home(homeId, homeOwner, homeName, homeAddress, area, propertyType, homeValue, buildYear);
        homeState = genson.serialize(home);
        stub.putStringState(homeId, homeState);
        return home;
    }

    public Home queryHome(final Context ctx, final String homeId) {
        ChaincodeStub stub= ctx.getStub();
        String homeState = stub.getStringState(homeId);
        if (homeState.isEmpty()) {
            String errorMessage = String.format("Home %s does not exist", homeId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, HomeTransferErrors.HOME_NOT_FOUND.toString());
        }
        Home home = genson.deserialize(homeState, Home.class);
        return home;
    }

    public Home changeHomeOwnership(final Context ctx, final String homeId, final String newHomeOwner) {
        ChaincodeStub stub= ctx.getStub();
        String homeState = stub.getStringState(homeId);
        if (homeState.isEmpty()) {
            String errorMessage = String.format("Home %s does not exist", homeId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, HomeTransferErrors.HOME_NOT_FOUND.toString());
        }
        Home home = genson.deserialize(homeState, Home.class);
        Home newHome = new Home(home.getHomeId(), newHomeOwner, home.getHomeName(), home.getHomeAddress(), home.getArea(),
                home.getPropertyType(), home.getHomeValue(), home.getBuildYear());
        String newHomeState = genson.serialize(newHome);
        stub.putStringState(homeId, newHomeState);
        return newHome;
    }

    public HomeInterface decorate(HomeInterface chaincodeInterface){
        return null;
    }
}
