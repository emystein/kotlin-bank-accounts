package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;

public class BankAccountBuilder {
    private final Customer accountOwner;
    private Dollars initialBalance;

    public BankAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public BankAccountBuilder withInitialBalance(Dollars amount) {
        this.initialBalance = amount;

        return this;
    }

    public BankAccount build() {
        if (initialBalance == null) {
            throw new MissingInitialBalanceException();
        }

        return new BankAccount(this.accountOwner, this.initialBalance);
    }
}
