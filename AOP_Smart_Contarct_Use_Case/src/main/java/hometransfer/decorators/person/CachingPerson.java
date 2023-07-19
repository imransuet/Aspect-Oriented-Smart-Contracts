package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;

import java.util.HashMap;
import java.util.Map;

public class CachingPerson extends AbstractPerson{

    private  Map<String, Person> personCache = new HashMap<>();

    public CachingPerson(PersonInterface person) {
        super(person);
    }

    public CachingPerson() {
    }

    @Override
    public PersonInterface decorate(PersonInterface chaincodeInterface) {
        System.out.println("Inside caching decorate of Home\n");
        return new CachingPerson(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        super.initLedger(ctx);
    }

    @Override
    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address,
                               final String dateOfBirth, final String emailAddress, final String phoneNumber, final String nationality) {
        Person person = super.addNewPerson(ctx, personId, personName, address, dateOfBirth, emailAddress, phoneNumber, nationality);
        personCache.put(personId, person);
        return person;
    }

    @Override
    public Person queryPerson(final Context ctx, final String personId) {
        if (personCache.containsKey(personId)) {
            System.out.println("Return value from Person Cache\n");
            return personCache.get(personId);
        }
        Person person = super.queryPerson(ctx, personId);
        personCache.put(personId, person);
        return person;
    }
}
