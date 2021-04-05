package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;

@Getter
public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final String currency;
    private final WithdrawalLimit withdrawalLimit;
    private final TransactionHistory transactionHistory;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, String currency, WithdrawalLimit withdrawalLimit, TransactionHistory transactionHistory) {
        this.owner = owner;
        this.currency = currency;
        this.withdrawalLimit = withdrawalLimit;
        this.transactionHistory = transactionHistory;
    }

    public Balance getInitialBalance() {
        return transactionHistory.first()
                .map(TransactionRecord::getBalance)
                .orElse(Balance.zero(currency));
    }

    public Balance getBalance() {
        return transactionHistory.sum()
                .orElse(Balance.zero(currency));
    }

    public Balance getPreviousBalance() {
        return transactionHistory.sumBeforeLast()
                .orElse(Balance.zero(currency));
    }

    public TransactionRecord deposit(Money amountToDeposit) {
        return Deposit.to(this).amount(amountToDeposit).execute();
    }

    public boolean withdrawalLimitAccepts(Withdrawal withdrawal) {
        return withdrawalLimit.accepts(withdrawal);
    }

    public TransactionRecord withdraw(Money amountToWithdraw) {
        return Withdrawal.from(this).amount(amountToWithdraw).execute();
    }

    public TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer) {
        return BankTransfer.from(this).to(creditAccount).amount(amountToTransfer).execute();
    }

    public void addTransactionRecord(TransactionRecord transactionRecord) {
        transactionHistory.add(transactionRecord);
    }
}
