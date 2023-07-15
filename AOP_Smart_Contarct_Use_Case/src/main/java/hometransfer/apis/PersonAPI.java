package hometransfer.apis;

import hometransfer.DecoratorManager;
import hometransfer.interfaces.PersonInterface;
import hometransfer.models.Person;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "PersonAPI",
        info = @Info(
                title = "Person Contract",
                description = "A Demonstration of Chaincode about Personal Data Management",
                version = "0.0.1-SNAPSHOT"))

@Default
public final class PersonAPI implements ContractInterface{
    DecoratorManager decoratorManager = DecoratorManager.getInstance();
    PersonInterface decoratedChaincode;

    @Transaction()
    public void initLedger(final Context ctx) {
        decoratedChaincode= decoratorManager.getPersonContract(ctx);
        decoratedChaincode.initLedger(ctx);
    }

    @Transaction()
    public Person addNewPerson(final Context ctx, final String personId, final String personName,
                               final String address, final String dateOfBirth, final String emailAddress,
                               final String phoneNumber, final String nationality) {
        decoratedChaincode= decoratorManager.getPersonContract(ctx);
        return decoratedChaincode.addNewPerson(ctx, personId, personName, address, dateOfBirth, emailAddress, phoneNumber, nationality);
    }

    @Transaction()
    public Person queryPerson(final Context ctx, final String personId) {
        decoratedChaincode= decoratorManager.getPersonContract(ctx);
        return decoratedChaincode.queryPerson(ctx, personId);
    }
}
