package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createCheckingAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckingAccountTest {
    private Customer francisco;
    private Customer mabel;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        var account = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);

        var amountToWithdraw = dollars10;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw110USDThenBalanceShouldBeMinus10USD() {
        var account = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);

        var amountToWithdraw = dollars110;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
        assertEquals(-10, account.getBalance().getAmount());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw300USDThenWithdrawalShouldBeRejected() {
        var account = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);

        assertThrows(InsufficientFundsException.class, () ->
                account.withdraw(dollars300));

        assertAccountKeepsInitialBalance(account);
    }


    @Test
    void givenADebitWith100USDBalanceWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        var debitAccount = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);
        var creditAccount = createCheckingAccountFor(mabel, dollars100, TestObjects.minus100DollarsLimit);

        var amountToTransfer = dollars10;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer110USDThenTheMoneyShouldBeTransferred() {
        var debitAccount = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);
        var creditAccount = createCheckingAccountFor(mabel, dollars100, TestObjects.minus100DollarsLimit);

        var amountToTransfer = dollars110;

        debitAccount.transfer(creditAccount, amountToTransfer);

        assertBalanceDecreasedBy(debitAccount, amountToTransfer);
        assertBalanceIncreasedBy(creditAccount, amountToTransfer);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer300USDThenTheMoneyShouldNotBeTransferred() {
        var debitAccount = createCheckingAccountFor(francisco, dollars100, TestObjects.minus100DollarsLimit);
        var creditAccount = createCheckingAccountFor(mabel, dollars100, TestObjects.minus100DollarsLimit);

        var amountToTransfer = dollars300;

        assertThrows(InsufficientFundsException.class, () -> debitAccount.transfer(creditAccount, amountToTransfer));

        assertAccountKeepsInitialBalance(debitAccount);
        assertAccountKeepsInitialBalance(creditAccount);
    }
}
