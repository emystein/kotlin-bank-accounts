package com.emilianomenendez.veritran;

public class Bank {
    public static BankAccountBuilder createAccountOwnedBy(Customer accountOwner) {
        return new BankAccountBuilder(accountOwner);
    }
}
