package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsAccountAssertions {
    public static void assertBalanceIncreasedBy(SavingsAccount account, Dollars amount) {
        assertEquals(account.getInitialBalance().plus(amount), account.getBalance());
    }

    public static void assertBalanceDecreasedBy(SavingsAccount account, Dollars amount) {
        assertEquals(account.getInitialBalance().minus(amount), account.getBalance());
    }

    public static void assertAccountKeepsInitialBalance(SavingsAccount account) {
        assertTrue(account.hasBalance(account.getInitialBalance()));
    }
}
