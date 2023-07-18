package hometransfer.decorators.account;

import com.owlike.genson.Genson;
import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class PreConditionAccount extends AbstractAccount {
    private final Genson genson = new Genson();

    public PreConditionAccount(AccountInterface accountInterface) {
        super(accountInterface);
    }

    public PreConditionAccount() {
    }

    @Override
    public AccountInterface decorate(AccountInterface chaincodeInterface) {
        System.out.println("Inside Precondition Account decorate method\n");
        return new PreConditionAccount(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        super.initLedger(ctx);
    }

    @Override
    public Account addNewAccount(final Context ctx, final String accountId, final String personId, final double balance) {
        if (ctx == null || accountId == null || personId == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (Integer.parseInt(accountId) <= 0 || Integer.parseInt(personId) <= 0) {
            throw new IllegalArgumentException("accountId and personId must be greater than zero");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance must be positive");
        }
        return super.addNewAccount(ctx, accountId, personId, balance);
    }

    @Override
    public Account queryAccount(final Context ctx, final String accountId) {
        if (ctx == null || accountId == null) {
            throw new IllegalArgumentException("Context and accountId cannot be null");
        }
        if (Integer.parseInt(accountId) <= 0) {
            throw new IllegalArgumentException("accountId must be greater than zero");
        }
        return super.queryAccount(ctx, accountId);
    }

    @Override
    public String transferBalance(final Context ctx, final String senderAccountId, final String receiverAccountId, double transferAmount) {
        if (ctx == null || senderAccountId == null || receiverAccountId == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (Integer.parseInt(senderAccountId) <= 0 || Integer.parseInt(receiverAccountId) <= 0) {
            throw new IllegalArgumentException("senderAccountId and receiverAccountId must be greater than zero");
        }
        if (transferAmount < 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        ChaincodeStub stub = ctx.getStub();
        String accountState = stub.getStringState(senderAccountId);

        Account senderAccount = genson.deserialize(accountState, Account.class);

        if (senderAccount.getBalance() < transferAmount) {
            throw new IllegalArgumentException("Sender account balance must be more than transfer amount");
        }
        return super.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
    }

}
