package hometransfer;
import org.hyperledger.fabric.contract.Context;
public class TimingHomeTransferDecorator extends HomeTransferDecorator {
    public TimingHomeTransferDecorator(HomeTransferInterface homeTransfer) {
        super(homeTransfer);
    }

    @Override
    public Home addNewHome(Context ctx, String id, String name, String area, String ownername, String value) {
        long startTime = System.currentTimeMillis();
        Home home = super.addNewHome(ctx, id, name, area, ownername, value);
        long endTime = System.currentTimeMillis();
        System.out.println("addNewHome execution time: " + (endTime - startTime) + "ms");
        return home;
    }
}