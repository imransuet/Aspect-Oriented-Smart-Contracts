package hometransfer.decorators.account;

import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;

public abstract  class AbstractAccount implements AccountInterface {

    protected AccountInterface chaincodeInterface;


    public AbstractAccount(AccountInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
    }
    public AbstractAccount() {
    }
    public AccountInterface decorate(AccountInterface chaincodeInterface) {
        this.chaincodeInterface = chaincodeInterface;
        return this;
    }

    public void initLedger(final Context ctx) {
        chaincodeInterface.initLedger(ctx);
    }

    public Account addNewAccount(final Context ctx, final String accountId,final String personId, final double balance) {
        return chaincodeInterface.addNewAccount(ctx, accountId,personId,  balance);
    }

    public Account queryAccount(final Context ctx, final String accountId) {
        return chaincodeInterface.queryAccount(ctx,  accountId);
    }

    public String transferBalance(Context ctx, String senderAccountId, String receiverAccountId, double transferAmount) {
        return chaincodeInterface.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
    }



}