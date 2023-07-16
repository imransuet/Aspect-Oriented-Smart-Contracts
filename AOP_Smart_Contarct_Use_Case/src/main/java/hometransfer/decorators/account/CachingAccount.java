package hometransfer.decorators.account;

import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;

import java.util.HashMap;
import java.util.Map;

public class CachingAccount extends AbstractAccount{

    private static final Map<String, Account> accountCache = new HashMap<>();

    public CachingAccount(AccountInterface homeTransfer) {
        super(homeTransfer);
    }

    public CachingAccount() {
    }

    @Override
    public AccountInterface decorate(AccountInterface chaincodeInterface) {
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
            System.out.println("Return value from cache\n");
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
        if (accountCache.containsKey(senderAccountId)) {
            accountCache.remove(senderAccountId);
            Account updatedSenderAccount = super.queryAccount(ctx, senderAccountId);
            accountCache.put(senderAccountId, updatedSenderAccount);
        }
        if (accountCache.containsKey(receiverAccountId)) {
            accountCache.remove(receiverAccountId);
            Account updatedReceiverAccount = super.queryAccount(ctx, receiverAccountId);
            accountCache.put(receiverAccountId, updatedReceiverAccount);
        }
        return result;
    }
}
