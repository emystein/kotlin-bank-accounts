package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;

public class TestObjects {
    public static SavingsAccount createSavingsAccountFor(Customer accountOwner, Dollars initialBalance) {
        return SavingsAccount.newAccountOwnedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }
}
