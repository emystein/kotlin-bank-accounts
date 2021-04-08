package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.balance.BalanceTimeline;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.bankaccount.transaction.transfer.Transfer;
import ar.com.flow.bankaccount.transaction.withdrawal.CurrentFundsLimit;
import ar.com.flow.bankaccount.transaction.withdrawal.Withdrawal;
import ar.com.flow.bankaccount.transaction.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @RequiredArgsConstructor
    public static class SavingsAccountBuilder {
        private final Customer accountOwner;
        private String currency = "USD";
        private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

        public SavingsAccountBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public SavingsAccountBuilder withdrawalLimit(WithdrawalLimit withdrawalLimit) {
            this.withdrawalLimit = withdrawalLimit;
            return this;
        }

        public SavingsAccount build() {
            var accountHistory = new InMemoryTransactionHistory();
            return new SavingsAccount(accountOwner, currency, withdrawalLimit, accountHistory);
        }
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

    public void deposit(Money amountToDeposit) {
        Deposit.to(this)
                .amount(amountToDeposit)
                .execute();
    }

    public void withdraw(Money amountToWithdraw) {
        Withdrawal.from(this)
                .limit(withdrawalLimit)
                .amount(amountToWithdraw)
                .execute();
    }

    public void transfer(BankAccount creditAccount, Money amountToTransfer) {
        Transfer.from(this)
                .to(creditAccount)
                .amount(amountToTransfer)
                .execute();
    }

    public void addTransactionRecord(TransactionRecord transactionRecord) {
        transactionHistory.add(transactionRecord);
    }
}
