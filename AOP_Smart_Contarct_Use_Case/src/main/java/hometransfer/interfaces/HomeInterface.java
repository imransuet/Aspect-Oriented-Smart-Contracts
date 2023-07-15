package hometransfer.interfaces;

import hometransfer.models.Home;
import org.hyperledger.fabric.contract.Context;

public interface HomeInterface {

     void initLedger(final Context ctx);
   Home addNewHome(final Context ctx, final String homeId, final String homeOwnerId,
                           final String homeName, final String homeAddress,final String area);
     Home queryHome(final Context ctx, final String  homeId);
    Home changeHomeOwnership(final Context ctx, final String homeId, String newHomeOwnerId);
       HomeInterface decorate(HomeInterface chaincodeInterface);
}
