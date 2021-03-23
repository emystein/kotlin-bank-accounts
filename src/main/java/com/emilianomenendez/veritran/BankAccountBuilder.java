package com.emilianomenendez.veritran;

public class BankAccountBuilder {
    private final Customer accountOwner;
    private Dollars initialBalance = new Dollars(0);

    public BankAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public BankAccountBuilder withInitialBalance(Dollars initialBalance) {
        this.initialBalance = initialBalance;
        return this;
    }

    public BankAccount build() {
        return new BankAccount(this.accountOwner, this.initialBalance);
    }
}
