package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;

public class LoggingPerson extends AbstractPerson {
    public LoggingPerson(PersonInterface homeTransfer) {
        super(homeTransfer);
    }

    public   PersonInterface decorate(PersonInterface chaincodeInterface) {

        System.out.printf("I am in decorate method");
        return  new LoggingPerson(chaincodeInterface);


    }
    public LoggingPerson() {
    }

    @Override
    public void initLedger(final Context ctx) {

        System.out.println("Before initLedger");
        super.initLedger(ctx);
        System.out.println("After initLedger");
    }
    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address){
        System.out.println("Before addNewPerson");
      Person person= super.addNewPerson(ctx,  personId,  personName,  address);
        System.out.println("After addNewPerson");
        return person;
    }
    public Person queryPerson(final Context ctx, final String personId){

        System.out.println("Before queryPersonById");
        Person person = super.queryPerson(ctx, personId);
        System.out.println("After queryHomeById");
        return person;

    }

}