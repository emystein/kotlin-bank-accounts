package com.emilianomenendez.veritran;

public class TestObjects {
    public static BankAccount createBankAccountFor(Customer accountOwner, Dollars initialBalance) {
        return BankAccount.newAccountOwnedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }
}
