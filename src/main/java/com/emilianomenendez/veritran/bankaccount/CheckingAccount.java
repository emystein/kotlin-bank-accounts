package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.money.Dollars;

public class CheckingAccount implements BankAccount {
    private final Customer owner;
    private Balance initialBalance;
    private Balance balance;
    private final WithdrawLimit withdrawLimit;

    public CheckingAccount(Customer owner, Dollars initialBalance, WithdrawLimit withdrawLimit) {
        this.owner = owner;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = this.initialBalance;
        this.withdrawLimit = withdrawLimit;
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
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
        balance = Withdraw.from(this).limitedBy(withdrawLimit).amount(amountToWithdraw);
    }

    @Override
    public void transfer(BankAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
