package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private Customer francisco = Customer.named("francisco");
    private Customer mabel = Customer.named("mabel");

    @Test
    void givenACustomerWhenCreateABankAccountThenItShouldBeOwnedByTheCustomer() {
        BankAccount createdAccount = BankAccount.newAccountOwnedBy(francisco).build();

        assertTrue(createdAccount.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerWhenCreateABankAccountWithoutInitialBalanceThenItShouldHaveInitialBalance0USD() {
        BankAccount createdAccount = BankAccount.newAccountOwnedBy(francisco).build();

        assertEquals(new Dollars(0), createdAccount.getBalance());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateABankAccountThenItShouldHaveInitialBalance() {
        BankAccount createdAccount = createBankAccountFor(francisco, new Dollars(100));

        assertEquals(new Dollars(100), createdAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        bankAccount.deposit(new Dollars(10));

        assertEquals(new Dollars(110), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDepositANegativeAmountThenItShouldRejectTheDeposit() {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.deposit(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() throws Exception {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        bankAccount.withdraw(new Dollars(10));

        assertEquals(new Dollars(90), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw100USDThenBalanceShouldBe0USD() throws Exception {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        bankAccount.withdraw(new Dollars(100));

        assertEquals(new Dollars(0), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdrawANegativeAmountThenWithdrawShouldBeRejected() {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        assertThrows(IllegalArgumentException.class, () ->
                bankAccount.withdraw(new Dollars(-10)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawShouldBeRejected() {
        BankAccount bankAccount = createBankAccountFor(francisco, new Dollars(100));

        assertThrows(OverdraftException.class, () ->
                bankAccount.withdraw(new Dollars(200)));

        assertEquals(new Dollars(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenTransfer10USDToADestinationAccountThenTheDestinationAccountShouldIncreaseBalanceIn10USD() throws OverdraftException {
        BankAccount sourceAccount = createBankAccountFor(francisco, new Dollars(100));
        BankAccount destinationAccount = createBankAccountFor(mabel, new Dollars(100));

        sourceAccount.transfer(destinationAccount, new Dollars(10));

        assertEquals(new Dollars(90), sourceAccount.getBalance());
        assertEquals(new Dollars(110), destinationAccount.getBalance());
    }

    private BankAccount createBankAccountFor(Customer accountOwner, Dollars initialBalance) {
        return BankAccount.newAccountOwnedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }
}
