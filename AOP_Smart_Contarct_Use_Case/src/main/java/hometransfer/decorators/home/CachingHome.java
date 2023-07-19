package hometransfer.decorators.home;

import hometransfer.interfaces.HomeInterface;
import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;

import java.util.HashMap;
import java.util.Map;

public class CachingHome extends AbstractHome{

    private  Map<String, Home> homeCache = new HashMap<>();

    public CachingHome(HomeInterface homeTransfer) {
        super(homeTransfer);
    }

    public CachingHome() {
    }

    @Override
    public HomeInterface decorate(HomeInterface chaincodeInterface) {
        System.out.println("Inside caching decorate of Home\n");
        return new CachingHome(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        super.initLedger(ctx);
    }

    @Override
    public Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress, final String area,
                           final String propertyType, final double homeValue, final int buildYear) {
        Home home = super.addNewHome(ctx, homeId, homeOwnerId, homeName, homeAddress, area, propertyType, homeValue, buildYear);
        homeCache.put(homeId, home);
        return home;
    }

    @Override
    public Home queryHome(final Context ctx, final String homeId) {
        if (homeCache.containsKey(homeId)) {
            System.out.println("Return value from Home Cache\n");
            return homeCache.get(homeId);
        }
        Home home = super.queryHome(ctx, homeId);
        homeCache.put(homeId, home);
        return home;
    }

    @Override
    public Home changeHomeOwnership(final Context ctx, final String homeId, final String newHomeOwnerId) {
        Home home = super.changeHomeOwnership(ctx, homeId, newHomeOwnerId);
        if (homeCache.containsKey(homeId)) {
            homeCache.remove(homeId);
        }

        homeCache.put(homeId, home);
        return home;
    }

}
