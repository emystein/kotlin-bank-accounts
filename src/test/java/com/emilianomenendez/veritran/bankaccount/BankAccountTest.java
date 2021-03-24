package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;
import com.emilianomenendez.veritran.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.createBankAccountFor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountTest {
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
                BankAccount.newAccountOwnedBy(francisco).build());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldBeOwnedByTheCustomer() {
        BankAccount createdAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertTrue(createdAccount.isOwnedBy(francisco));
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
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
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
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        debitAccount.transfer(creditAccount, Dollars.amount(10));

        assertTrue(debitAccount.hasBalance(Dollars.amount(90)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(110)));
    }
}
