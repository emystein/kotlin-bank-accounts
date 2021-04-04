package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;

import java.time.LocalDateTime;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final WithdrawalLimit withdrawalLimit;
    private Balance initialBalance;
    private Balance balance;
    private TransactionHistory history;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, WithdrawalLimit withdrawalLimit, Money initialBalance, TransactionHistory history) {
        this.owner = owner;
        this.withdrawalLimit = withdrawalLimit;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = Balance.create(initialBalance);
        this.history = history;
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    public Balance getInitialBalance() {
        return initialBalance;
    }

    public Balance getBalance() {
        return balance;
    }

    public TransactionRecord deposit(Money amountToDeposit) {
        balance = balance.plus(amountToDeposit);

        var transactionRecord = new TransactionRecord(LocalDateTime.now(), amountToDeposit);
        history.add(transactionRecord);
        return transactionRecord;
    }

    public boolean withdrawalLimitAccepts(Withdrawal withdrawal) {
        return withdrawalLimit.accepts(withdrawal);
    }

    public TransactionRecord withdraw(Money amountToWithdraw) {
        balance = Withdrawal.from(this)
                .amount(amountToWithdraw)
                .execute();

        var transactionRecord = new TransactionRecord(LocalDateTime.now(), Balance.negative(amountToWithdraw));
        history.add(transactionRecord);
        return transactionRecord;
    }

    public void transfer(BankAccount creditAccount, Money amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
