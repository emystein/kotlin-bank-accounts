package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.SameAccountTransferException;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createSavingsAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
    private Customer francisco;
    private BankAccount franciscosAccount;
    private Customer mabel;
    private BankAccount mabelsAccount;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        franciscosAccount = createSavingsAccountFor(francisco, dollars100);
        mabel = Customer.named("mabel");
        mabelsAccount = createSavingsAccountFor(mabel, dollars100);
    }

    @Test
    void givenAnInitialStateWhenCreateABankAccountThenItShouldHaveBasicInformationSet() {
        var account = SavingsAccount.ownedBy(francisco).currency("ARS").build();

        assertEquals("ARS", account.getCurrency());
        assertEquals(Balance.zero("ARS"), account.getBalance());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldBeOwnedByTheCustomer() {
        assertTrue(franciscosAccount.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        assertAccountKeepsInitialBalance(franciscosAccount);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenPreviousBalanceShouldBeInitialBalance() {
        franciscosAccount.deposit(dollars10);

        assertEquals(Balance.create(dollars100), franciscosAccount.getPreviousBalance());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        franciscosAccount.deposit(dollars10);

        assertBalanceIncreasedBy(franciscosAccount, dollars10);
    }

    @Test
    void givenAnAccountWhenDepositThenTransactionHistoryShouldContainTheTransactionRecord() {
        var transactionRecord = franciscosAccount.deposit(dollars10);

        assertTrue(franciscosAccount.getTransactionHistory().contains(transactionRecord));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        franciscosAccount.withdraw(dollars10);

        assertBalanceDecreasedBy(franciscosAccount, dollars10);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars200));

        assertAccountKeepsInitialBalance(franciscosAccount);
    }

    @Test
    void givenAWithdrawalWhenGetItsAmountThenTheAmountShouldBeNegative() {
        TransactionRecord transactionRecord = franciscosAccount.withdraw(dollars10);

        assertEquals(Balance.negative(dollars10), transactionRecord.getBalance());
    }

    @Test
    void givenAnAccountWhenWithdrawThenTransactionHistoryShouldContainTheTransactionRecord() {
        var transactionRecord = franciscosAccount.withdraw(dollars10);

        assertTrue(franciscosAccount.getTransactionHistory().contains(transactionRecord));
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars10);

        assertAmountMovedFromTo(franciscosAccount, mabelsAccount, dollars10);
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheTransactionRecordShouldStoreTheCreditBalance() {
        var transactionRecord = franciscosAccount.transfer(mabelsAccount, dollars10);

        assertEquals(Balance.positive(dollars10), transactionRecord.getBalance());
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheTransactionHistoryShouldContainTheTransactionRecord() {
        var transactionRecord = franciscosAccount.transfer(mabelsAccount, dollars10);

        assertTrue(mabelsAccount.getTransactionHistory().contains(transactionRecord));
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        assertThrows(InsufficientFundsException.class, () ->
                franciscosAccount.transfer(mabelsAccount, dollars200));

        assertAccountKeepsInitialBalance(franciscosAccount);
        assertAccountKeepsInitialBalance(mabelsAccount);
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        assertThrows(SameAccountTransferException.class, () ->
                franciscosAccount.transfer(franciscosAccount, dollars10));

        assertAccountKeepsInitialBalance(franciscosAccount);
    }
}
