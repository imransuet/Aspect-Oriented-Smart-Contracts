package hometransfer.contract;

import com.owlike.genson.Genson;
import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class AccountContract implements AccountInterface {
    private enum AccountTransferErrors {
        ACCOUNT_NOT_FOUND,
        ACCOUNT_ALREADY_EXISTS,
        INSUFFICIENT_BALANCE
    }
    private final Genson genson = new Genson();
    @Override
    public void initLedger(Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String personId = "1"; // replaced Person object with personId
        Account account = new Account("1", personId, 5000);
        System.out.println("Account Initialization successful");

        String accountState = genson.serialize(account);
        stub.putStringState("1", accountState); //add to ledger
    }

    @Override
    public Account addNewAccount(Context ctx,String accountId, String personId, double balance) { // replaced Person object with personId
        ChaincodeStub stub = ctx.getStub();
        String accountState = stub.getStringState(accountId);

        if (!accountState.isEmpty()) {
            String errorMessage = String.format("Account %s already exists", accountId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AccountTransferErrors.ACCOUNT_ALREADY_EXISTS.toString());
        }

        Account account = new Account(accountId, personId, balance); // replaced Person object with personId
        accountState = genson.serialize(account);
        stub.putStringState(accountId, accountState);

        return account;
    }

    @Override
    public Account queryAccount(Context ctx, String accountId) {
        ChaincodeStub stub = ctx.getStub();
        String accountState = stub.getStringState(accountId);

        if (accountState.isEmpty()) {
            String errorMessage = String.format("Account %s does not exist", accountId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AccountTransferErrors.ACCOUNT_NOT_FOUND.toString());
        }

        Account account = genson.deserialize(accountState, Account.class);
        return account;
    }


    @Override
    public String transferBalance(Context ctx, String senderAccountId, String receiverAccountId, double transferAmount) {
        ChaincodeStub stub = ctx.getStub();

        // Fetch sender account details
        String senderAccountState = stub.getStringState(senderAccountId);
        if (senderAccountState.isEmpty()) {
            String errorMessage = String.format("Sender Account %s does not exist", senderAccountId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AccountTransferErrors.ACCOUNT_NOT_FOUND.toString());
        }

        // Fetch receiver account details
        String receiverAccountState = stub.getStringState(receiverAccountId);
        if (receiverAccountState.isEmpty()) {
            String errorMessage = String.format("Receiver Account %s does not exist", receiverAccountId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AccountTransferErrors.ACCOUNT_NOT_FOUND.toString());
        }

        // Deserialize sender and receiver account details
        Account senderAccount = genson.deserialize(senderAccountState, Account.class);
        Account receiverAccount = genson.deserialize(receiverAccountState, Account.class);

        // Validate that sender account has enough balance
        if (senderAccount.getBalance() < transferAmount) {
            String errorMessage = String.format("Sender Account %s does not have enough balance", senderAccountId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AccountTransferErrors.INSUFFICIENT_BALANCE.toString());
        }

        // Create updated accounts after transferring the balance
        Account updatedSenderAccount = new Account(senderAccount.getAccountId(), senderAccount.getPersonId(), senderAccount.getBalance() - transferAmount); // replaced getPerson() with getPersonId()
        Account updatedReceiverAccount = new Account(receiverAccount.getAccountId(), receiverAccount.getPersonId(), receiverAccount.getBalance() + transferAmount); // replaced getPerson() with getPersonId()

        // Serialize updated accounts
        String updatedSenderAccountState = genson.serialize(updatedSenderAccount);
        String updatedReceiverAccountState = genson.serialize(updatedReceiverAccount);

        // Put updated accounts to the ledger
        stub.putStringState(senderAccountId, updatedSenderAccountState);
        stub.putStringState(receiverAccountId, updatedReceiverAccountState);

        // Return a success message
        return String.format("Successfully transferred %.2f balance from account %s to account %s", transferAmount, senderAccountId, receiverAccountId);
    }


    public AccountInterface decorate(AccountInterface chaincodeInterface){
        return null;
    }
}
