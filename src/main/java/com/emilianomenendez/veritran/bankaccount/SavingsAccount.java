package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private Balance initialBalance;
    private Balance balance;
    private WithdrawLimit withdrawLimit;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = this.initialBalance;
        this.withdrawLimit = new ZeroLowerLimit();
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
