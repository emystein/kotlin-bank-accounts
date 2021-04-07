package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.transfer.BankTransfer;
import ar.com.flow.bankaccount.withdrawal.Withdrawal;
import ar.com.flow.money.Money;
import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
import lombok.Getter;

@Getter
public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final String currency;
    private final WithdrawalLimit withdrawalLimit;
    private final TransactionHistory transactionHistory;
    private final BalanceTimeline balanceTimeline;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, String currency, WithdrawalLimit withdrawalLimit, TransactionHistory transactionHistory) {
        this.owner = owner;
        this.currency = currency;
        this.withdrawalLimit = withdrawalLimit;
        this.transactionHistory = transactionHistory;
        this.balanceTimeline = new BalanceTimeline(currency, transactionHistory);
    }

    public Balance getInitialBalance() {
        return balanceTimeline.initialSnapshot();
    }

    public Balance getBalance() {
        return balanceTimeline.currentSnapshot();
    }

    public Balance getPreviousBalance() {
        return balanceTimeline.previousSnapshot();
    }

    public TransactionRecord deposit(Money amountToDeposit) {
        return Deposit.to(this)
                .amount(amountToDeposit)
                .execute();
    }

    public TransactionRecord withdraw(Money amountToWithdraw) {
        return Withdrawal.from(this)
                .limit(withdrawalLimit)
                .amount(amountToWithdraw)
                .execute();
    }

    public TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer) {
        return BankTransfer.from(this)
                .to(creditAccount)
                .amount(amountToTransfer)
                .execute();
    }

    public void addTransactionRecord(TransactionRecord transactionRecord) {
        transactionHistory.add(transactionRecord);
    }
}
