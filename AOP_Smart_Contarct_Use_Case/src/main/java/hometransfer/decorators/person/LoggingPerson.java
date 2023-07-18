package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggingPerson extends AbstractPerson {
    private static final Logger logger = Logger.getLogger(LoggingPerson.class.getName());

    public LoggingPerson(PersonInterface person) {
        super(person);
    }

    public LoggingPerson() {
    }

    public PersonInterface decorate(PersonInterface chaincodeInterface) {
        System.out.println("Inside Logging Person decorate method\n");

        PersonInterface result = new LoggingPerson(chaincodeInterface);

        return result;
    }

    @Override
    public void initLedger(final Context ctx) {
        logger.log(Level.INFO, "Entering method: initLedger");
        super.initLedger(ctx);
        logger.log(Level.INFO, "Exiting method: initLedger");
    }

    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address,
                               final String dateOfBirth, final String emailAddress, final String phoneNumber, final String nationality){
        logger.log(Level.INFO, "Entering method: addNewPerson");
        Person person = super.addNewPerson(ctx,  personId,  personName,  address, dateOfBirth, emailAddress, phoneNumber, nationality);
        logger.log(Level.INFO, "Exiting method: addNewPerson");
        return person;
    }

    public Person queryPerson(final Context ctx, final String personId){
        logger.log(Level.INFO, "Entering method: queryPerson");
        Person person = super.queryPerson(ctx, personId);
        logger.log(Level.INFO, "Exiting method: queryPerson");
        return person;
    }
}
