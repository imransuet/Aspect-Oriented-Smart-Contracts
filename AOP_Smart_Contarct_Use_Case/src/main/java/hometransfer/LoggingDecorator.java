package hometransfer;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.nio.charset.StandardCharsets;

public class LoggingDecorator extends AbstractDecorator {
    public LoggingDecorator(HomeTransferInterface homeTransfer) {
        super(homeTransfer);
    }

    public   HomeTransferInterface decorate(HomeTransferInterface chaincodeInterface) {

        System.out.printf("I am in decorate method");
        return  new LoggingDecorator(chaincodeInterface);


    }
    public LoggingDecorator() {
    }


    public boolean shouldApplyForTransaction(Context ctx) {

        System.out.println("i entered shouldApplyForTransaction ");
        ChaincodeStub stub= ctx.getStub();
        byte[] loggingBytes = stub.getTransient().get("logging");
        if (loggingBytes != null) {
            String loggingValue = new String(loggingBytes, StandardCharsets.UTF_8);
            System.out.println("i left shouldApplyForTransaction with true ");
            return loggingValue.equalsIgnoreCase("true");
        }
        System.out.println("i left shouldApplyForTransaction with false ");
        return false;
    }
    @Override
    public void initLedger(final Context ctx) {

        System.out.println("Before initLedger");
        super.initLedger(ctx);
        System.out.println("After initLedger");
    }

    @Override
    public Home addNewHome(final Context ctx, String id, String name, String area, String ownername, String value) {

        System.out.println("Before addNewHome");
        Home home = super.addNewHome(ctx, id, name, area, ownername, value);
        System.out.println("After addNewHome");
        return home;
    }

    @Override
    public Home queryHome(final Context ctx, String id) {
        System.out.println("Before queryHomeById");
        Home home = super.queryHome(ctx, id);
        System.out.println("After queryHomeById");
        return home;
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, String id, String newHomeOwner) {
        System.out.println("Before changeHomeOwnership");
        Home home = super.changeHomeOwnership(ctx, id, newHomeOwner);
        System.out.println("After changeHomeOwnership");
        return home;
    }
}