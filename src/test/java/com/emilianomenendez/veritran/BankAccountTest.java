package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private Customer customer = Customer.named("francisco");

    @Test
    void givenACustomerWhenCreateABankAccountThenItShouldBeOwnedByTheCustomer() {
        BankAccount createdAccount = Bank.newAccountOwnedBy(customer).build();

        assertTrue(createdAccount.isOwnedBy(customer));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateABankAccountThenItShouldHaveInitialBalance() {
        BankAccount createdAccount = createBankAccountWith100USDBalance();

        assertEquals(new Dollars(100), createdAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = createBankAccountWith100USDBalance();

        bankAccount.deposit(new Dollars(10));

        assertEquals(new Dollars(110), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDepositANegativeAmountThenItShouldRejectTheDeposit() {
        BankAccount bankAccount = createBankAccountWith100USDBalance();

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.deposit(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() throws Exception {
        BankAccount bankAccount = createBankAccountWith100USDBalance();

        bankAccount.withdraw(new Dollars(10));

        assertEquals(new Dollars(90), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdrawANegativeAmountThenItShouldRejectTheWithdraw() {
        BankAccount bankAccount = createBankAccountWith100USDBalance();

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.withdraw(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw200USDThenItShouldRejectTheWithdraw() {
        BankAccount bankAccount = createBankAccountWith100USDBalance();

        assertThrows(OverdraftException.class, () ->
                bankAccount.withdraw(new Dollars(200)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }


    private BankAccount createBankAccountWith100USDBalance() {
        return Bank.newAccountOwnedBy(customer)
                .withInitialBalance(new Dollars(100))
                .build();
    }
}
