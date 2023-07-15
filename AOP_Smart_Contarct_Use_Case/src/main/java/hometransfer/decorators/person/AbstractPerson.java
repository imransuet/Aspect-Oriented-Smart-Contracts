package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;

public abstract class AbstractPerson implements PersonInterface {

    protected PersonInterface chaincodeInterface;

    public AbstractPerson(PersonInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
    }
    public AbstractPerson() {
    }

    public PersonInterface decorate(PersonInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
        return this;
    }

    public void initLedger(final Context ctx) {
        chaincodeInterface.initLedger(ctx);
    }

    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address,
                               final String dateOfBirth, final String emailAddress, final String phoneNumber, final String nationality) {
        return  chaincodeInterface.addNewPerson(ctx, personId,  personName,  address, dateOfBirth, emailAddress, phoneNumber, nationality);
    }

    public Person queryPerson(final Context ctx, final String personId) {
        return chaincodeInterface.queryPerson(ctx, personId);
    }
}
