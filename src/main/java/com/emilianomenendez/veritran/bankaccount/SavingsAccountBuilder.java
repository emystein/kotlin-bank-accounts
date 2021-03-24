package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private Dollars initialBalance;

    public SavingsAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public SavingsAccountBuilder withInitialBalance(Dollars amount) {
        this.initialBalance = amount;

        return this;
    }

    public SavingsAccount build() {
        if (initialBalance == null) {
            throw new MissingInitialBalanceException();
        }

        return new SavingsAccount(this.accountOwner, this.initialBalance);
    }
}
