package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
