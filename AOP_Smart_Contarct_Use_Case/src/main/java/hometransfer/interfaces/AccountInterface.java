package hometransfer.interfaces;

import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;

public interface AccountInterface {

   void initLedger(final Context ctx);
     Account addNewAccount(final Context ctx, final String accountId, final String personId, final double balance);
    Account queryAccount(final Context ctx, final String accountId);
    String  transferBalance(final Context ctx, final String senderAccountId, final String receiverAccountId, double transferAmount);

      AccountInterface decorate(AccountInterface chaincodeInterface);
}
