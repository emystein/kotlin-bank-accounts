package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private Balance initialBalance;
    private Balance balance;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = this.initialBalance;
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

    public void deposit(Dollars amountToDeposit) {
        balance = balance.plus(amountToDeposit);
    }

    public void withdraw(Dollars amountToWithdraw) {
        if (balance.isLessThan(amountToWithdraw)) {
            throw new InsufficientFundsException();
        }

        balance = balance.minus(amountToWithdraw);
    }

    public void transfer(SavingsAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
