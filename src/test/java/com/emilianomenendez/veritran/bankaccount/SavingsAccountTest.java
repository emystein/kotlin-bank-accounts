package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;
import com.emilianomenendez.veritran.InsufficientFundsException;
import com.emilianomenendez.veritran.bankaccount.transfer.SameAccountTransferException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        SavingsAccount account = createSavingsAccountFor(francisco, Dollars.amount(100));

        assertTrue(account.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        SavingsAccount account = createSavingsAccountFor(francisco, Dollars.amount(100));

        assertTrue(account.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        SavingsAccount account = createSavingsAccountFor(francisco, Dollars.amount(100));

        account.deposit(Dollars.amount(10));

        assertTrue(account.hasBalance(Dollars.amount(110)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        SavingsAccount account = createSavingsAccountFor(francisco, Dollars.amount(100));

        account.withdraw(Dollars.amount(10));

        assertTrue(account.hasBalance(Dollars.amount(90)));
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        SavingsAccount account = createSavingsAccountFor(francisco, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () -> account.withdraw(Dollars.amount(200)));

        assertTrue(account.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, Dollars.amount(100));
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, Dollars.amount(100));

        debitAccount.transfer(creditAccount, Dollars.amount(10));

        assertTrue(debitAccount.hasBalance(Dollars.amount(90)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(110)));
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, Dollars.amount(100));
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                debitAccount.transfer(creditAccount, Dollars.amount(110)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, Dollars.amount(100));

        assertThrows(SameAccountTransferException.class, () ->
                debitAccount.transfer(debitAccount, Dollars.amount(10)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
    }
}
