package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.SameAccountTransferException;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createSavingsAccountFor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsAccountTest {
    private Customer francisco;
    private Customer mabel;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");
    }

    @Test
    void givenACustomerWhenCreateAnAccountWithoutInitialBalanceThenTheAccountShouldNotBeCreated() {
        assertThrows(MissingInitialBalanceException.class, () ->
                SavingsAccount.newAccountOwnedBy(francisco).build());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldBeOwnedByTheCustomer() {
        SavingsAccount account = createSavingsAccountFor(francisco, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);

        assertTrue(account.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        Dollars initialBalance = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100;

        SavingsAccount account = createSavingsAccountFor(francisco, initialBalance);

        assertTrue(account.hasBalance(initialBalance));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        Dollars initialBalance = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100;

        SavingsAccount account = createSavingsAccountFor(francisco, initialBalance);

        Dollars amountToDeposit = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;

        account.deposit(amountToDeposit);

        assertTrue(account.hasBalance(initialBalance.plus(amountToDeposit)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        Dollars initialBalance = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100;

        SavingsAccount account = createSavingsAccountFor(francisco, initialBalance);

        Dollars amountToWithdraw = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;

        account.withdraw(amountToWithdraw);

        assertTrue(account.hasBalance(initialBalance.minus(amountToWithdraw)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        SavingsAccount account = createSavingsAccountFor(francisco, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                account.withdraw(account.getBalance().plus(com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100)));

        assertAccountKeepsInitialBalance(account);
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);

        Dollars amountToTransfer = com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                debitAccount.transfer(creditAccount, debitAccount.getBalance().plus(com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100)));

        assertAccountKeepsInitialBalance(debitAccount);
        assertAccountKeepsInitialBalance(creditAccount);
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars100);

        assertThrows(SameAccountTransferException.class, () ->
                debitAccount.transfer(debitAccount, com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10));

        assertAccountKeepsInitialBalance(debitAccount);
    }
}