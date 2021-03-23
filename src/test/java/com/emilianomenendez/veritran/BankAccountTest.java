package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountTest {
    private Customer customer = Customer.named("francisco");

    @Test
    void givenACustomerWhenCreateAnAccountForTheCustomerThenItShouldBeOwnedByTheCustomer() {
        BankAccount createdAccount = Bank.createAccountOwnedBy(customer).build();

        assertTrue(createdAccount.isOwnedBy(customer));
    }

    @Test
    void givenACustomerAndAnInitialBalanceWhenCreateAnAccountForTheCustomerThenItShouldHaveInitialBalance() {
        BankAccount createdAccount = Bank.createAccountOwnedBy(customer)
                .withInitialBalance(new Dollars(100))
                .build();

        assertEquals(new Dollars(100), createdAccount.getBalance());
    }
}
