package com.emilianomenendez.veritran;

import com.google.common.base.Preconditions;

public class BankAccount {
    private Customer owner;
    private Dollars balance;

    public BankAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public boolean isOwnedBy(Customer customer) {
        return this.owner == customer;
    }

    public Dollars getBalance() {
        return balance;
    }

    public void deposit(Dollars amountToDeposit) {
        assertPositive(amountToDeposit);

        balance = balance.plus(amountToDeposit);
    }

    public void withdraw(Dollars amountToWithdraw) throws OverdraftException  {
        assertPositive(amountToWithdraw);
        assertSufficientFunds(amountToWithdraw);

        balance = balance.minus(amountToWithdraw);
    }

    private void assertSufficientFunds(Dollars amountToWithdraw) throws OverdraftException {
        if (amountToWithdraw.isGreaterThan(balance)) {
            throw new OverdraftException();
        };
    }

    private void assertPositive(Dollars amountToCheck) {
        Preconditions.checkArgument(amountToCheck.isPositive());
    }
}
