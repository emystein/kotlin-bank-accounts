package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdraw.Withdraw;
import com.emilianomenendez.veritran.bankaccount.withdraw.WithdrawLimit;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final WithdrawLimit withdrawLimit;
    private Balance initialBalance;
    private Balance balance;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, WithdrawLimit withdrawLimit, Dollars initialBalance) {
        this.owner = owner;
        this.withdrawLimit = withdrawLimit;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = Balance.create(initialBalance);
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    @Override
    public WithdrawLimit getWithdrawLimit() {
        return withdrawLimit;
    }

    @Override
    public Balance getInitialBalance() {
        return initialBalance;
    }

    @Override
    public Balance getBalance() {
        return balance;
    }

    @Override
    public void deposit(Dollars amountToDeposit) {
        balance = balance.plus(amountToDeposit);
    }

    @Override
    public void withdraw(Dollars amountToWithdraw) {
        balance = Withdraw.from(this).amount(amountToWithdraw);
    }

    @Override
    public void transfer(BankAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
