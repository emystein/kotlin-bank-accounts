package com.emilianomenendez.veritran;

import com.google.common.base.Preconditions;

public class BankAccount {
    private Customer owner;
    private Dollars balance;

    public static BankAccountBuilder newAccountOwnedBy(Customer accountOwner) {
        return new BankAccountBuilder(accountOwner);
    }

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

    public void withdraw(Dollars amountToWithdraw) throws InsufficientFundsException {
        assertPositive(amountToWithdraw);
        assertSufficientFunds(amountToWithdraw);

        balance = balance.minus(amountToWithdraw);
    }

    public void transfer(BankAccount destinationAccount, Dollars amountToTransfer) throws InsufficientFundsException {
        assertThisIsDifferentAccountThan(destinationAccount);

        withdraw(amountToTransfer);

        destinationAccount.deposit(amountToTransfer);
    }

    private void assertThisIsDifferentAccountThan(BankAccount accountToCompare) {
        if (this == accountToCompare) {
            throw new SameAccountException();
        }
    }

    private void assertSufficientFunds(Dollars amountToWithdraw) throws InsufficientFundsException {
        if (amountToWithdraw.isGreaterThan(balance)) {
            throw new InsufficientFundsException();
        }
    }

    private void assertPositive(Dollars amountToCheck) {
        Preconditions.checkArgument(amountToCheck.isPositive());
    }
}
