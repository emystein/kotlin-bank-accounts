package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.SameAccountTransferException;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createCheckingAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {
    private Customer francisco;
    private Customer mabel;
    private NumberLowerLimit limitMinus100Dollars;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");
        limitMinus100Dollars = new NumberLowerLimit(Balance.negative(Dollars.amount(100)));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldBeOwnedByTheCustomer() {
        BankAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        assertTrue(account.isOwnedBy(francisco));
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        BankAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        assertAccountKeepsInitialBalance(account);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        Dollars amountToDeposit = dollars10;

        account.deposit(amountToDeposit);

        assertBalanceIncreasedBy(account, amountToDeposit);
    }


    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        Dollars amountToWithdraw = dollars10;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw110USDThenBalanceShouldBeMinus10USD() {
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        Dollars amountToWithdraw = dollars110;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
        assertEquals(-10, account.getBalance().getAmount());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw300USDThenWithdrawalShouldBeRejected() {
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        assertThrows(InsufficientFundsException.class, () ->
                account.withdraw(dollars300));

        assertAccountKeepsInitialBalance(account);
    }


    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        CheckingAccount debitAccount = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);
        CheckingAccount creditAccount = createCheckingAccountFor(mabel, dollars100, limitMinus100Dollars);

        Dollars amountToTransfer = dollars10;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldBeTransferred() {
        CheckingAccount debitAccount = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);
        CheckingAccount creditAccount = createCheckingAccountFor(mabel, dollars100, limitMinus100Dollars);

        Dollars amountToTransfer = dollars200;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        CheckingAccount debitAccount = createCheckingAccountFor(francisco, dollars100, limitMinus100Dollars);

        assertThrows(SameAccountTransferException.class, () ->
                debitAccount.transfer(debitAccount, dollars10));

        assertAccountKeepsInitialBalance(debitAccount);
    }
}
