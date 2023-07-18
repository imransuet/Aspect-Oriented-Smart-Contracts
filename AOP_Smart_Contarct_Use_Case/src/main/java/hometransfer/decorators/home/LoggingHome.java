package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggingHome extends AbstractHome {
    private static final Logger logger = Logger.getLogger(LoggingHome.class.getName());

    public LoggingHome(HomeInterface homeTransfer) {
        super(homeTransfer);
    }

    public LoggingHome() {
    }

    public HomeInterface decorate(HomeInterface chaincodeInterface) {
        System.out.println("Inside Logging Home decorate method\n");
        return new LoggingHome(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        logger.log(Level.INFO, "Entering method: initLedger");
        super.initLedger(ctx);
        logger.log(Level.INFO, "Exiting method: initLedger");
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        logger.log(Level.INFO, "Entering method: addNewHome");
        Home home = super.addNewHome(ctx, homeId, homeOwnerId, homeName, homeAddress, area, propertyType, homeValue, buildYear);
        logger.log(Level.INFO, "Exiting method: addNewHome");
        return home;
    }

    @Override
    public Home queryHome(final Context ctx, final String id) {
        logger.log(Level.INFO, "Entering method: queryHome");
        Home home = super.queryHome(ctx, id);
        logger.log(Level.INFO, "Exiting method: queryHome");
        return home;
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwnerId) {
        logger.log(Level.INFO, "Entering method: changeHomeOwnership");
        Home home = super.changeHomeOwnership(ctx, id, newHomeOwnerId);
        logger.log(Level.INFO, "Exiting method: changeHomeOwnership");
        return home;
    }
}
