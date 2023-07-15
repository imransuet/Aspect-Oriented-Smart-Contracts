package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;

public abstract  class AbstractHome implements HomeInterface {

    protected HomeInterface chaincodeInterface;

    public AbstractHome(HomeInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
    }

    public AbstractHome() {
    }

    public HomeInterface decorate(HomeInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
        return this;
    }

    @Override
    public void initLedger(final Context ctx) {
        chaincodeInterface.initLedger(ctx);
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwner,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        return chaincodeInterface.addNewHome(ctx,homeId, homeOwner, homeName, homeAddress, area, propertyType, homeValue, buildYear);
    }

    @Override
    public Home queryHome(final Context ctx, String id) {
        return chaincodeInterface.queryHome(ctx,id);
    }

    @Override
    public Home changeHomeOwnership(final Context ctx , final String id, final String newHomeOwner) {
        return chaincodeInterface.changeHomeOwnership(ctx, id, newHomeOwner);
    }
}
