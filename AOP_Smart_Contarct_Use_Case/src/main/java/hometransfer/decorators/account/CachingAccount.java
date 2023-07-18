package hometransfer.decorators.account;

import com.owlike.genson.Genson;
import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.HashMap;
import java.util.Map;

public class CachingAccount extends AbstractAccount{
    private final Genson genson = new Genson();
    private static final Map<String, Account> accountCache = new HashMap<>();

    public CachingAccount(AccountInterface account) {
        super(account);
    }

    public CachingAccount() {
    }

    @Override
    public AccountInterface decorate(AccountInterface chaincodeInterface) {
        System.out.println("Inside Caching Account decorate method\n");
        return new CachingAccount(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        super.initLedger(ctx);
    }

    @Override
    public Account addNewAccount(final Context ctx, final String accountId, final String personId, final double balance) {
        Account account = super.addNewAccount(ctx, accountId, personId, balance);
        accountCache.put(accountId, account);
        return account;
    }

    @Override
    public Account queryAccount(final Context ctx, final String accountId) {
        if (accountCache.containsKey(accountId)) {
            System.out.println("Return value from Account Cache\n");
            return accountCache.get(accountId);
        }
        Account account = super.queryAccount(ctx, accountId);
        accountCache.put(accountId, account);
        return account;
    }


    @Override
    public String transferBalance(final Context ctx, final String senderAccountId, final String receiverAccountId, double transferAmount) {
        String result = super.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
        // Updating cache with fresh data after the transaction
        ChaincodeStub stub = ctx.getStub();

        if (accountCache.containsKey(senderAccountId)) {
            accountCache.remove(senderAccountId);
            String updatedSenderAccountState = stub.getStringState(senderAccountId);
            Account updatedSenderAccount = genson.deserialize(updatedSenderAccountState, Account.class);
            accountCache.put(senderAccountId, updatedSenderAccount);
        }
        if (accountCache.containsKey(receiverAccountId)) {
            accountCache.remove(receiverAccountId);
            String updatedReceiverAccountState = stub.getStringState(receiverAccountId);
            Account updatedReceiverAccount = genson.deserialize(updatedReceiverAccountState, Account.class);
            accountCache.put(receiverAccountId, updatedReceiverAccount);
        }
        return result;
    }

}
