package hometransfer;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "HomeTransfer",
        info = @Info(
                title = "HomeTransfer contract",
                description = "A Sample Home transfer example with logging",
                version = "0.0.1-SNAPSHOT"))


@Default
public final class HomeTransfer implements ContractInterface{
    private final HomeTransferInterface homeTransfer;

    public HomeTransfer() {
        homeTransfer = new LoggingHomeTransferDecorator(new HomeTransferBL());
    }


    @Transaction()
    public void initLedger(final Context ctx) {
        homeTransfer.initLedger(ctx);
    }


    @Transaction()
    public Home addNewHome(final Context ctx, final String id, final String name, final String area, final String ownername, final String value) {
        return homeTransfer.addNewHome(ctx, id, name, area, ownername, value);
    }


    @Transaction()
    public Home queryHomeById(final Context ctx, final String id) {
        return homeTransfer.queryHomeById(ctx, id);
    }


    @Transaction()
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwner) {
        return homeTransfer.changeHomeOwnership(ctx, id, newHomeOwner);
    }
}