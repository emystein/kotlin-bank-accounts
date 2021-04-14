package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.transfer.SameAccountException;
import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.BankAccountAssert.assertThat;
import static ar.com.flow.bankaccount.OptionalTransactionRecordAssert.assertThat;
import static ar.com.flow.bankaccount.TestObjects.*;
import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SavingsAccountTest {
    private BankAccount franciscosAccount;
    private BankAccount mabelsAccount;

    @BeforeEach
    void setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100);
        mabelsAccount = createSavingsAccountFor(mabel, dollars100);
    }

    @Test
    void createdAccountHasBasicInformation() {
        var account = SavingsAccount.builder().owner(francisco).currency("ARS").build();

        assertEquals(francisco, account.getOwner());
        assertEquals("ARS", account.getCurrency());
        assertEquals(Balance.zero("ARS"), account.getBalance());
    }

    @Test
    void depositIncrementsFunds() {
        franciscosAccount.deposit(dollars10);

        assertThat(franciscosAccount).increasedFunds(dollars10);
    }

    @Test
    void withdrawalDecrementsFunds() {
        franciscosAccount.withdraw(dollars10);

        assertThat(franciscosAccount).decreasedFunds(dollars10);
    }

    @Test
    void canNotWithdrawAmountGreaterThanAvailableFunds() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars200));

        assertThat(franciscosAccount).keepsInitialBalance();
    }

    @Test
    void withdrawalAddsTransactionRecordToStatement() {
        franciscosAccount.withdraw(dollars10);

        assertThat(franciscosAccount.getStatement().last())
                .isPresent()
                .hasAction(Action.Withdrawal)
                .hasBalance(Balance.negative(dollars10));
    }

    @Test
    void transferAmountLessThanAvailableFunds() {
        franciscosAccount.transfer(mabelsAccount, dollars10);

        assertThat(franciscosAccount).decreasedFunds(dollars10);

        assertThat(mabelsAccount).increasedFunds(dollars10);

        assertThat(franciscosAccount.getStatement().last())
                .isPresent()
                .hasAction(Action.Transfer)
                .hasBalance(Balance.negative(dollars10))
                .hasCreditAccount(mabelsAccount);

        assertThat(mabelsAccount.getStatement().last())
                .isPresent()
                .hasAction(Action.Transfer)
                .hasBalance(Balance.positive(dollars10));
    }

    @Test
    void canNotTransferAmountGreaterThanAvailableFunds() {
        assertThrows(InsufficientFundsException.class, () ->
                franciscosAccount.transfer(mabelsAccount, dollars200));

        assertThat(franciscosAccount).keepsInitialBalance();
        assertThat(mabelsAccount).keepsInitialBalance();
    }

    @Test
    void canNotTransferToSelf() {
        assertThrows(SameAccountException.class, () ->
                franciscosAccount.transfer(franciscosAccount, dollars10));

        assertThat(franciscosAccount).keepsInitialBalance();
    }
}
