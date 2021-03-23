package com.emilianomenendez.veritran;

public class Bank {
    public static BankAccountBuilder newAccountOwnedBy(Customer accountOwner) {
        return new BankAccountBuilder(accountOwner);
    }
}
