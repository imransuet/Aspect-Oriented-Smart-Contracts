package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;

public class LoggingHome extends AbstractHome {
    public LoggingHome(HomeInterface homeTransfer) {
        super(homeTransfer);
    }

    public   HomeInterface decorate(HomeInterface chaincodeInterface) {

        return  new LoggingHome(chaincodeInterface);


    }
    public LoggingHome() {
    }

    @Override
    public void initLedger(final Context ctx) {

        System.out.println("HomeTransfer: Before initLedger");
        super.initLedger(ctx);
        System.out.println("HomeTransfer:After initLedger");
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress, final String area) {

        System.out.println("HomeTransfer:Before addNewHome");
        Home home = super.addNewHome(ctx, homeId, homeOwnerId,homeName, homeAddress, area);
        System.out.println("HomeTransfer:After addNewHome");
        return home;
    }

    @Override
    public Home queryHome(final Context ctx, final String id) {
        System.out.println("HomeTransfer:Before queryHomeById");
        Home home = super.queryHome(ctx, id);
        System.out.println("HomeTransfer:After queryHomeById");
        return home;
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwnerId) {
        System.out.println("HomeTransfer:Before changeHomeOwnership");
        Home home = super.changeHomeOwnership(ctx, id, newHomeOwnerId);
        System.out.println("AHomeTransfer:fter changeHomeOwnership");
        return home;
    }
}