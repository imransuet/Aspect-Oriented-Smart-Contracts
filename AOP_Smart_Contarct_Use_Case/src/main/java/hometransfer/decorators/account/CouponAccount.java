package hometransfer.decorators.account;

import hometransfer.interfaces.AccountInterface;
import hometransfer.models.Account;
import org.hyperledger.fabric.contract.Context;

public class CouponAccount extends AbstractAccount {

    public CouponAccount(AccountInterface account) {
        super(account);
    }

    public CouponAccount() {
    }

    @Override
    public AccountInterface decorate(AccountInterface chaincodeInterface) {
        System.out.println("Inside Coupon Account decorate method\n");
        return new CouponAccount(chaincodeInterface);
    }

    @Override
    public void initLedger(final Context ctx) {
        super.initLedger(ctx);
    }

    @Override
    public Account addNewAccount(final Context ctx, final String accountId, final String personId, final double balance) {
        return super.addNewAccount(ctx, accountId, personId, balance);
    }

    @Override
    public Account queryAccount(final Context ctx, final String accountId) {
        return super.queryAccount(ctx, accountId);
    }

    @Override
    public String transferBalance(final Context ctx, final String senderAccountId, final String receiverAccountId, double transferAmount) {
        double discountedAmount = transferAmount  * 0.8;
        return super.transferBalance(ctx, senderAccountId, receiverAccountId, discountedAmount);
    }
}
