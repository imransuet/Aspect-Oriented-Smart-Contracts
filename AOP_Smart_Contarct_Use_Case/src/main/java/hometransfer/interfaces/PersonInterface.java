package hometransfer.interfaces;

import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;

public interface PersonInterface {

    void initLedger(final Context ctx);
    Person addNewPerson(final Context ctx, final String personId, final String personName,
                        final String address, final String dateOfBirth, final String emailAddress,
                        final String phoneNumber, final String nationality);
    Person queryPerson(final Context ctx, final String personId);
    PersonInterface decorate(PersonInterface chaincodeInterface);

}
