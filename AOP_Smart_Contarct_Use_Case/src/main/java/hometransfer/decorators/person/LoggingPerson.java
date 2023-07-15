package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingPerson extends AbstractPerson {
    private static final Logger logger = LoggerFactory.getLogger(LoggingPerson.class);

    public LoggingPerson(PersonInterface homeTransfer) {
        super(homeTransfer);
    }

    public LoggingPerson() {
    }

    public PersonInterface decorate(PersonInterface chaincodeInterface) {
        logger.info("I am in decorate method");
        return new LoggingPerson(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        logger.info("Before initLedger");
        super.initLedger(ctx);
        logger.info("After initLedger");
    }

    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address,
                               final String dateOfBirth, final String emailAddress, final String phoneNumber, final String nationality){
        logger.info("Before addNewPerson");
        Person person = super.addNewPerson(ctx,  personId,  personName,  address, dateOfBirth, emailAddress, phoneNumber, nationality);
        logger.info("After addNewPerson");
        return person;
    }

    public Person queryPerson(final Context ctx, final String personId){
        logger.info("Before queryPersonById");
        Person person = super.queryPerson(ctx, personId);
        logger.info("After queryHomeById");
        return person;
    }
}
