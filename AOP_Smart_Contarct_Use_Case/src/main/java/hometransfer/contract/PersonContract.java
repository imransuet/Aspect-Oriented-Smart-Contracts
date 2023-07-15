package hometransfer.contract;

import com.owlike.genson.Genson;
import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class PersonContract implements PersonInterface {

    private final Genson genson = new Genson();

    private enum PersonErrors {
        PERSON_NOT_FOUND,
        PERSON_ALREADY_EXISTS
    }

    @Override
    public void initLedger(Context ctx) {
        ChaincodeStub stub= ctx.getStub();

        Person person = new Person("10", "Shah Saheb", "Kashmir", "1888-11-25", "shah@example.com", "1234567890", "india");

        System.out.println("Initialization successful");

        String personState = genson.serialize(person);

        stub.putStringState("1", personState ); //add to ledger
    }

    @Override
    public Person addNewPerson(Context ctx, String personId, String personName, String address,
                               String dateOfBirth, String emailAddress, String phoneNumber, String nationality) {

        ChaincodeStub stub= ctx.getStub();
        String personState = stub.getStringState(personId);
        if (!personState.isEmpty()) {
            String errorMessage = String.format("Person %s already exists", personId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, PersonContract.PersonErrors.PERSON_ALREADY_EXISTS.toString());
        }

        Person person = new Person(personId, personName, address, dateOfBirth, emailAddress, phoneNumber, nationality);
        personState = genson.serialize(person);
        stub.putStringState(personId, personState);
        return person;
    }


    @Override
    public Person queryPerson(Context ctx, String personId) {
        ChaincodeStub stub= ctx.getStub();
        String personState = stub.getStringState(personId); //Query from ledger

        if (personState.isEmpty()) {
            String errorMessage = String.format("Person %s does not exist", personId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, PersonContract.PersonErrors.PERSON_NOT_FOUND.toString());
        }

        Person person = genson.deserialize(personState, Person.class); //deserializing JSON to java Object
        return person;
    }

    public PersonInterface decorate(PersonInterface chaincodeInterface){
        return null;
    }
}
