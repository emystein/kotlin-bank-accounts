package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.bankaccount.transaction.transfer.Transfer;
import ar.com.flow.bankaccount.transaction.withdrawal.CurrentFundsLimit;
import ar.com.flow.bankaccount.transaction.withdrawal.Withdrawal;
import ar.com.flow.bankaccount.transaction.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final String currency;
    @Builder.Default
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();
    @Builder.Default
    private TransactionHistory transactionHistory = new InMemoryTransactionHistory();

    public Balance getInitialBalance() {
        return transactionHistory.getInitialBalance()
                .orElse(Balance.zero(getCurrency()));
    }

    public Balance getBalance() {
        return transactionHistory.getCurrentBalance()
                .orElse(Balance.zero(getCurrency()));
    }

    public Balance getPreviousBalance() {
        return transactionHistory.getPreviousBalance()
                .orElse(Balance.zero(getCurrency()));
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
