package hometransfer.decorators.account;

import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;

public class LoggingAccount extends AbstractAccount {
    public LoggingAccount(AccountInterface homeTransfer) {
        super(homeTransfer);
    }

    public   AccountInterface decorate(AccountInterface chaincodeInterface) {

        System.out.printf("I am in decorate method");
        return  new LoggingAccount(chaincodeInterface);


    }
    public LoggingAccount() {
    }

    @Override
    public void initLedger(final Context ctx) {

        System.out.println("Before initLedger");
        super.initLedger(ctx);
        System.out.println("After initLedger");
    }

    public Account addNewAccount(final Context ctx,final String accountId, final String personId, final double balance){
        System.out.println("Before creating new Account");
        Account account= super.addNewAccount(ctx,accountId,personId,balance);
        System.out.println("After creating new Account");
        return account;
    }
    public Account queryAccount(final Context ctx, final String accountId){

        System.out.println("Before queryPersonById");
         return super.queryAccount(ctx, accountId);

    }
    public String transferBalance(Context ctx, String senderAccountId, String receiverAccountId, double transferAmount)
    {
        System.out.println("Before Transfer Balance");
        return super.transferBalance(ctx, senderAccountId,  receiverAccountId,  transferAmount);
    }


}