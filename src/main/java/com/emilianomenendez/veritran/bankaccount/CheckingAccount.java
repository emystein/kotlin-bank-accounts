package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

public class CheckingAccount implements BankAccount {
    private final Customer owner;
    private Balance initialBalance;
    private Balance balance;

    public CheckingAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = this.initialBalance;
    }

    public Balance getInitialBalance() {
        return initialBalance;
    }

    public Balance getBalance() {
        return balance;
    }

    public void withdraw(Dollars amountToWithdraw) {
        balance = balance.minus(amountToWithdraw);
    }
}
