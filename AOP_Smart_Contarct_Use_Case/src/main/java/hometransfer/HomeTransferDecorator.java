package hometransfer;

import org.hyperledger.fabric.contract.Context;

public class HomeTransferDecorator implements HomeTransferInterface {

    protected HomeTransferInterface chaincodeInterface;

    public HomeTransferDecorator(HomeTransferInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
    }

    @Override
    public void initLedger(Context ctx) {

        chaincodeInterface.initLedger(ctx);
    }

    @Override
    public Home addNewHome(Context ctx, String id, String name, String area, String ownername, String value) {

        return  chaincodeInterface.addNewHome(ctx, id, name, area, ownername, value);

    }

    @Override
    public Home queryHomeById(Context ctx, String id) {
        return chaincodeInterface.queryHomeById(ctx,id);
    }

    @Override
    public Home changeHomeOwnership(Context ctx, String id, String newHomeOwner) {
        return chaincodeInterface.changeHomeOwnership(ctx, id, newHomeOwner);
    }
}
