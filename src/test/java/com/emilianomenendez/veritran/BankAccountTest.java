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
    void givenACustomerWhenCreateABankAccountWithoutInitialBalanceThenItShouldHaveInitialBalance0USD() {
        BankAccount createdAccount = Bank.newAccountOwnedBy(customer).build();

        assertEquals(new Dollars(0), createdAccount.getBalance());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateABankAccountThenItShouldHaveInitialBalance() {
        BankAccount createdAccount = createBankAccountWithInitialBalance(new Dollars(100));

        assertEquals(new Dollars(100), createdAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        bankAccount.deposit(new Dollars(10));

        assertEquals(new Dollars(110), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDepositANegativeAmountThenItShouldRejectTheDeposit() {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.deposit(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() throws Exception {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        bankAccount.withdraw(new Dollars(10));

        assertEquals(new Dollars(90), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw100USDThenBalanceShouldBe0USD() throws Exception {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        bankAccount.withdraw(new Dollars(100));

        assertEquals(new Dollars(0), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdrawANegativeAmountThenWithdrawShouldBeRejected() {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.withdraw(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawShouldBeRejected() {
        BankAccount bankAccount = createBankAccountWithInitialBalance(new Dollars(100));

        assertThrows(OverdraftException.class, () ->
                bankAccount.withdraw(new Dollars(200)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    private BankAccount createBankAccountWithInitialBalance(Dollars balance) {
        return Bank.newAccountOwnedBy(customer)
                .withInitialBalance(balance)
                .build();
    }
}
