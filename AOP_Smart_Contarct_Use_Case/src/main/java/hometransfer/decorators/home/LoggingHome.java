package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHome extends AbstractHome {
    private static final Logger logger = LoggerFactory.getLogger(LoggingHome.class);

    public LoggingHome(HomeInterface homeTransfer) {
        super(homeTransfer);
    }

    public LoggingHome() {
    }

    public HomeInterface decorate(HomeInterface chaincodeInterface) {
        return new LoggingHome(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        logger.info("HomeTransfer: Before initLedger");
        super.initLedger(ctx);
        logger.info("HomeTransfer: After initLedger");
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        logger.info("HomeTransfer: Before addNewHome");
        Home home = super.addNewHome(ctx, homeId, homeOwnerId,homeName, homeAddress, area, propertyType, homeValue, buildYear);
        logger.info("HomeTransfer: After addNewHome");
        return home;
    }

    @Override
    public Home queryHome(final Context ctx, final String id) {
        logger.info("HomeTransfer: Before queryHomeById");
        Home home = super.queryHome(ctx, id);
        logger.info("HomeTransfer: After queryHomeById");
        return home;
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, final String id, final String newHomeOwnerId) {
        logger.info("HomeTransfer: Before changeHomeOwnership");
        Home home = super.changeHomeOwnership(ctx, id, newHomeOwnerId);
        logger.info("HomeTransfer: After changeHomeOwnership");
        return home;
    }
}
