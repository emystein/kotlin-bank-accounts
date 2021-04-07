package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountAssertions {
    public static void assertAmountMovedFromTo(BankAccount debitAccount, BankAccount creditAccount, Money amountToTransfer) {
        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    public static void assertBalanceIncreasedBy(BankAccount account, Money amount) {
        assertEquals(account.getPreviousBalance().plus(amount), account.getBalance());
    }

    public static void assertBalanceDecreasedBy(BankAccount account, Money amount) {
        assertEquals(account.getPreviousBalance().minus(amount), account.getBalance());
    }

    public static void assertAccountKeepsInitialBalance(BankAccount account) {
        assertEquals(account.getInitialBalance(), account.getBalance());
    }

    public static void assertAccountsKeepsInitialBalance(BankAccount... accounts) {
        Arrays.stream(accounts).forEach(SavingsAccountAssertions::assertAccountKeepsInitialBalance);
    }
}
