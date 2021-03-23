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
        BankAccount createdAccount = Bank.newAccountOwnedBy(customer)
                .withInitialBalance(new Dollars(100))
                .build();

        assertEquals(new Dollars(100), createdAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = Bank.newAccountOwnedBy(customer)
                .withInitialBalance(new Dollars(100))
                .build();

        bankAccount.deposit(new Dollars(10));

        assertEquals(new Dollars(110), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDepositANegativeAmountThenItShouldRejectTheDeposit() {
        BankAccount bankAccount = Bank.newAccountOwnedBy(customer)
                .withInitialBalance(new Dollars(100))
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.deposit(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }
}
