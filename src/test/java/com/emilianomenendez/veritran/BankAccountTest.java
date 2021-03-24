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

        assertEquals(Dollars.amount(0), createdAccount.getBalance());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateABankAccountThenItShouldHaveInitialBalance() {
        BankAccount createdAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertEquals(Dollars.amount(100), createdAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        bankAccount.deposit(Dollars.amount(10));

        assertEquals(Dollars.amount(110), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() throws Exception {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        bankAccount.withdraw(Dollars.amount(10));

        assertEquals(Dollars.amount(90), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw100USDThenBalanceShouldBe0USD() throws Exception {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        bankAccount.withdraw(Dollars.amount(100));

        assertEquals(Dollars.amount(0), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawShouldBeRejected() {
        BankAccount bankAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                bankAccount.withdraw(Dollars.amount(200)));

        assertEquals(Dollars.amount(100), bankAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenTransfer10USDToADestinationAccountThenTheDestinationAccountShouldIncreaseBalanceIn10USD() throws InsufficientFundsException, SameAccountException {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        debitAccount.transfer(creditAccount, Dollars.amount(10));

        assertEquals(Dollars.amount(90), debitAccount.getBalance());
        assertEquals(Dollars.amount(110), creditAccount.getBalance());
    }

    @Test
    void givenABankAccountWith100USDBalanceWhenTransfer110USDToADestinationAccountThenTheTransferShouldBeRejectedDueToInsufficientFunds() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                debitAccount.transfer(creditAccount, Dollars.amount(110)));

        assertEquals(Dollars.amount(100), debitAccount.getBalance());
        assertEquals(Dollars.amount(100), creditAccount.getBalance());
    }

    @Test
    void givenABankAccountWhenTransferToTheSameAccountThenTheTransferShouldBeRejected() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(SameAccountException.class, () ->
                debitAccount.transfer(debitAccount, Dollars.amount(10)));

        assertEquals(Dollars.amount(100), debitAccount.getBalance());
    }

    private BankAccount createBankAccountFor(Customer accountOwner, Dollars initialBalance) {
        return BankAccount.newAccountOwnedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }
}
