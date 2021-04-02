package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.SameAccountTransferException;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createSavingsAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
    private Customer francisco;
    private Customer mabel;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldBeOwnedByTheCustomer() {
        var account = createSavingsAccountFor(francisco, dollars100);

        assertTrue(account.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        var account = createSavingsAccountFor(francisco, dollars100);

        assertAccountKeepsInitialBalance(account);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        var account = createSavingsAccountFor(francisco, dollars100);

        var amountToDeposit = dollars10;

        account.deposit(amountToDeposit);

        assertBalanceIncreasedBy(account, amountToDeposit);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        var account = createSavingsAccountFor(francisco, dollars100);

        var amountToWithdraw = dollars10;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        var account = createSavingsAccountFor(francisco, dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                account.withdraw(dollars200));

        assertAccountKeepsInitialBalance(account);
    }

    @Test
    void givenAWithdrawalWhenGetItsAmountThenTheAmountShouldBeNegative() {
        var account = createSavingsAccountFor(francisco, dollars100);

        var amountToDeposit = dollars10;

        AccountMovement movement = account.withdraw(amountToDeposit);

        assertEquals(Balance.negative(amountToDeposit), movement.getAmount());
    }


    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        var debitAccount = createSavingsAccountFor(francisco, dollars100);
        var creditAccount = createSavingsAccountFor(mabel, dollars100);

        var amountToTransfer = dollars10;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        var debitAccount = createSavingsAccountFor(francisco, dollars100);
        var creditAccount = createSavingsAccountFor(mabel, dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                debitAccount.transfer(creditAccount, dollars200));

        assertAccountKeepsInitialBalance(debitAccount);
        assertAccountKeepsInitialBalance(creditAccount);
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        var debitAccount = createSavingsAccountFor(francisco, dollars100);

        assertThrows(SameAccountTransferException.class, () ->
                debitAccount.transfer(debitAccount, dollars10));

        assertAccountKeepsInitialBalance(debitAccount);
    }
}
