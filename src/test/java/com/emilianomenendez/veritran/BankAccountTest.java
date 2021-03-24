package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.TestObjectFactories.createBankAccountFor;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private Customer francisco = Customer.named("francisco");
    private Customer mabel = Customer.named("mabel");

    @Test
    void givenACustomerWhenCreateAnAccountForTheCustomerThenTheAccountShouldBeOwnedByTheCustomer() {
        BankAccount createdAccount = BankAccount.newAccountOwnedBy(francisco).build();

        assertTrue(createdAccount.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerWhenCreateAnAccountForTheCustomerThenTheAccountShouldHaveBalance0USD() {
        BankAccount createdAccount = BankAccount.newAccountOwnedBy(francisco).build();

        assertTrue(createdAccount.hasBalance(Dollars.amount(0)));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        BankAccount createdAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertTrue(createdAccount.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        bankAccount.deposit(Dollars.amount(10));

        assertTrue(bankAccount.hasBalance(Dollars.amount(110)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() throws Exception {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        bankAccount.withdraw(Dollars.amount(10));

        assertTrue(bankAccount.hasBalance(Dollars.amount(90)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                bankAccount.withdraw(Dollars.amount(200)));

        assertTrue(bankAccount.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() throws InsufficientFundsException, SameAccountException {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        debitAccount.transfer(creditAccount, Dollars.amount(10));

        assertTrue(debitAccount.hasBalance(Dollars.amount(90)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(110)));
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                debitAccount.transfer(creditAccount, Dollars.amount(110)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(SameAccountException.class, () ->
                debitAccount.transfer(debitAccount, Dollars.amount(10)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
    }
}
