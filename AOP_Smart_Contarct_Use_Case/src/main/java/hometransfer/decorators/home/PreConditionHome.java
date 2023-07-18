package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;

public class PreConditionHome extends AbstractHome {

    public PreConditionHome(HomeInterface homeInterface) {
        super(homeInterface);
    }

    public PreConditionHome() {
    }

    @Override
    public HomeInterface decorate(HomeInterface chaincodeInterface) {
        System.out.println("Inside Precondition Home decorate method\n");
        return new PreConditionHome(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        super.initLedger(ctx);
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        if (ctx == null || homeId == null || homeOwnerId == null || homeName == null || homeAddress == null ||
                area == null || propertyType == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (Integer.parseInt(homeId) <= 0 || Integer.parseInt(homeOwnerId) <= 0) {
            throw new IllegalArgumentException("homeId and homeOwnerId must be greater than zero");
        }
        if (homeValue <= 0) {
            throw new IllegalArgumentException("homeValue must be greater than zero");
        }
        if (buildYear <= 1920) {
            throw new IllegalArgumentException("buildYear must be more than 1920");
        }
        return super.addNewHome(ctx, homeId, homeOwnerId, homeName, homeAddress, area, propertyType, homeValue, buildYear);
    }

    @Override
    public Home queryHome(final Context ctx, final String homeId) {
        if (ctx == null || homeId == null) {
            throw new IllegalArgumentException("Context and homeId cannot be null");
        }
        if (Integer.parseInt(homeId) <= 0) {
            throw new IllegalArgumentException("homeId must be greater than zero");
        }
        return super.queryHome(ctx, homeId);
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, final String homeId, final String newHomeOwnerId) {
        if (ctx == null || homeId == null || newHomeOwnerId == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (Integer.parseInt(homeId) <= 0 || Integer.parseInt(newHomeOwnerId) <= 0) {
            throw new IllegalArgumentException("homeId and newHomeOwnerId must be greater than zero");
        }
        return super.changeHomeOwnership(ctx, homeId, newHomeOwnerId);
    }
}
