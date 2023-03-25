package hometransfer;
import org.hyperledger.fabric.contract.Context;
public class LoggingHomeTransferDecorator extends HomeTransferDecorator {
    public LoggingHomeTransferDecorator(HomeTransferInterface homeTransfer) {
        super(homeTransfer);
    }

    @Override
    public void initLedger(Context ctx) {
        System.out.println("Before initLedger");
        super.initLedger(ctx);
        System.out.println("After initLedger");
    }

    @Override
    public Home addNewHome(Context ctx, String id, String name, String area, String ownername, String value) {
        System.out.println("Before addNewHome");
        Home home = super.addNewHome(ctx, id, name, area, ownername, value);
        System.out.println("After addNewHome");
        return home;
    }

    @Override
    public Home queryHomeById(Context ctx, String id) {
        System.out.println("Before queryHomeById");
        Home home = super.queryHomeById(ctx, id);
        System.out.println("After queryHomeById");
        return home;
    }

    @Override
    public Home changeHomeOwnership(Context ctx, String id, String newHomeOwner) {
        System.out.println("Before changeHomeOwnership");
        Home home = super.changeHomeOwnership(ctx, id, newHomeOwner);
        System.out.println("After changeHomeOwnership");
        return home;
    }
}