package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountAssertions {
    public static void assertBalanceIncreasedBy(BankAccount account, Money amount) {
        assertEquals(account.getInitialBalance().plus(amount), account.getBalance());
    }

    public static void assertBalanceDecreasedBy(BankAccount account, Money amount) {
        assertEquals(account.getInitialBalance().minus(amount), account.getBalance());
    }

    public static void assertAccountKeepsInitialBalance(BankAccount account) {
        assertEquals(account.getInitialBalance(), account.getBalance());
    }
}
