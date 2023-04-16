package hometransfer;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public abstract  class AbstractDecorator implements HomeTransferInterface {

    protected HomeTransferInterface chaincodeInterface;


    public AbstractDecorator(HomeTransferInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
    }
    public AbstractDecorator() {
    }

    public HomeTransferInterface decorate(HomeTransferInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
        return this;
    }

    @Override
    public void initLedger(final Context ctx) {

        chaincodeInterface.initLedger(ctx);
    }

    @Override
    public Home addNewHome(final Context ctx, String id, String name, String area, String ownername, String value) {

        return  chaincodeInterface.addNewHome(ctx, id, name, area, ownername, value);

    }

    @Override
    public Home queryHome(final Context ctx, String id) {
        return chaincodeInterface.queryHome(ctx,id);
    }

    @Override
    public Home changeHomeOwnership(final Context ctx , String id, String newHomeOwner) {
        return chaincodeInterface.changeHomeOwnership(ctx, id, newHomeOwner);
    }

    public abstract boolean shouldApplyForTransaction(Context ctx);
}
