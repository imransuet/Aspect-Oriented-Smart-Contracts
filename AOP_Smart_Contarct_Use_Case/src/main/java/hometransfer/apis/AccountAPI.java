package hometransfer.apis;

import hometransfer.DecoratorManager;
import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(
        name = "AccountAPI",
        info = @Info(
                title = "Account contract",
                description = "A Demonstration of Chaincode about Home Ownership Transfer Balance",
                version = "0.0.1-SNAPSHOT"))
public class AccountAPI implements ContractInterface {

    AccountInterface decoratedChaincode;

    DecoratorManager decoratorManager = DecoratorManager.getInstance();


    @Transaction()
    public void initLedger(Context ctx) {
        decoratedChaincode= decoratorManager.getAccountContract(ctx);
            decoratedChaincode.initLedger(ctx);
    }

    @Transaction()
    public Account addNewAccount(Context ctx, final String accountId,String personId, double balance) {
        decoratedChaincode= decoratorManager.getAccountContract(ctx);

        return decoratedChaincode.addNewAccount(ctx, accountId,personId, balance);
    }

    @Transaction()
    public Account queryAccount(Context ctx, final String accountId) {
        decoratedChaincode= decoratorManager.getAccountContract(ctx);

        return decoratedChaincode.queryAccount(ctx,accountId);
    }

    @Transaction()
    String transferBalance(Context ctx, String senderAccountId, String receiverAccountId, double transferAmount){
        decoratedChaincode= decoratorManager.getAccountContract(ctx);
        return decoratedChaincode.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
    }

}
