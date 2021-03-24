package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccountBuilder {
    private final Customer accountOwner;

    public SavingsAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public SavingsAccount withInitialBalance(Dollars initialBalance) {
        return new SavingsAccount(this.accountOwner, initialBalance);
    }
}
