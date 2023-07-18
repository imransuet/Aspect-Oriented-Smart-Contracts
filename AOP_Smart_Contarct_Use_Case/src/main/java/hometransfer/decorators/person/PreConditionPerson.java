package hometransfer.decorators.person;

import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;

import java.util.regex.Pattern;

public class PreConditionPerson extends AbstractPerson{

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_REGEX =
            Pattern.compile("^[+]?[0-9]{10,13}$"); // Adjust the phone pattern according to your needs
    private static final Pattern COUNTRY_REGEX =
            Pattern.compile("^[A-Z][a-z]*(?: [A-Z][a-z]*)*$"); // Adjust the country pattern according to your needs

    public PreConditionPerson(PersonInterface personInterface) {
        super(personInterface);
    }

    public PreConditionPerson() {
    }

    @Override
    public PersonInterface decorate(PersonInterface chaincodeInterface) {
        System.out.println("Inside Precondition Person decorate method\n");
        return new PreConditionPerson(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        super.initLedger(ctx);
    }

    @Override
    public Person addNewPerson(final Context ctx, final String personId, final String personName, final String address,
                               final String dateOfBirth, final String emailAddress, final String phoneNumber, final String nationality) {
        if (ctx == null || personId == null || personName == null || address == null || dateOfBirth == null || emailAddress == null || phoneNumber == null || nationality == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (Integer.parseInt(personId) <= 0) {
            throw new IllegalArgumentException("personId must be greater than zero");
        }
        if (!EMAIL_REGEX.matcher(emailAddress).find()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!PHONE_REGEX.matcher(phoneNumber).find()) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (!COUNTRY_REGEX.matcher(nationality).find()) {
            throw new IllegalArgumentException("Invalid country name format");
        }
        return super.addNewPerson(ctx, personId, personName, address, dateOfBirth, emailAddress, phoneNumber, nationality);
    }

    @Override
    public Person queryPerson(final Context ctx, final String personId) {
        if (ctx == null || personId == null) {
            throw new IllegalArgumentException("Context and personId cannot be null");
        }
        if (Integer.parseInt(personId) <= 0) {
            throw new IllegalArgumentException("personId must be greater than zero");
        }
        return super.queryPerson(ctx, personId);
    }
}
