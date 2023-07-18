package hometransfer.decorators.account;

import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggingAccount extends AbstractAccount {
    private static final Logger logger = Logger.getLogger(LoggingAccount.class.getName());

    public LoggingAccount(AccountInterface account) {
        super(account);
    }

    public AccountInterface decorate(AccountInterface chaincodeInterface) {
        System.out.println("Inside Logging Account decorate method\n");;
        return new LoggingAccount(chaincodeInterface);
    }

    public LoggingAccount() {
    }

    @Override
    public void initLedger(final Context ctx) {
        logger.log(Level.INFO, "Entering method: initLedger");
        super.initLedger(ctx);
        logger.log(Level.INFO, "Exiting method: initLedger");
    }

    public Account addNewAccount(final Context ctx, final String accountId, final String personId, final double balance) {
        logger.log(Level.INFO, "Entering method: addNewAccount");
        Account account = super.addNewAccount(ctx, accountId, personId, balance);
        logger.log(Level.INFO, "Exiting method: addNewAccount");
        return account;
    }

    public Account queryAccount(final Context ctx, final String accountId) {
        logger.log(Level.INFO, "Entering method: queryAccount");
        Account account = super.queryAccount(ctx, accountId);
        logger.log(Level.INFO, "Exiting method: queryAccount");
        return account;
    }

    public String transferBalance(Context ctx, String senderAccountId, String receiverAccountId, double transferAmount) {
        logger.log(Level.INFO, "Entering method: transferBalance");
        String result = super.transferBalance(ctx, senderAccountId, receiverAccountId, transferAmount);
        logger.log(Level.INFO, "Exiting method: transferBalance");
        return result;
    }
}
