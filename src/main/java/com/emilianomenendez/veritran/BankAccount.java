package com.emilianomenendez.veritran;

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
        balance = balance.plus(amountToDeposit);
    }
}
