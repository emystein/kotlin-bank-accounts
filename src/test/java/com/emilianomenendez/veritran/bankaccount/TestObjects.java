package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;

public class TestObjects {
    public static BankAccount createBankAccountFor(Customer accountOwner, Dollars initialBalance) {
        return BankAccount.newAccountOwnedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }
}
