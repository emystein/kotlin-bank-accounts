package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createCheckingAccountFor;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.minusDollars100Limit;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckingAccountTest {
    private Customer francisco;
    private BankAccount franciscosAccount;
    private Customer mabel;
    private BankAccount mabelsAccount;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        franciscosAccount = createCheckingAccountFor(francisco, dollars100, minusDollars100Limit);
        mabel = Customer.named("mabel");
        mabelsAccount = createCheckingAccountFor(mabel, dollars100, minusDollars100Limit);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        franciscosAccount.withdraw(dollars10);

        assertBalanceDecreasedBy(franciscosAccount, dollars10);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw110USDThenBalanceShouldBeMinus10USD() {
        franciscosAccount.withdraw(dollars110);

        assertEquals(Balance.negative(dollars10), franciscosAccount.getBalance());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw300USDThenWithdrawalShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars300));

        assertAccountKeepsInitialBalance(franciscosAccount);
    }


    @Test
    void givenADebitWith100USDBalanceWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars10);

        assertAmountMovedFromTo(franciscosAccount, mabelsAccount, dollars10);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer110USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars110);

        assertAmountMovedFromTo(franciscosAccount, mabelsAccount, dollars110);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer300USDThenTheMoneyShouldNotBeTransferred() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.transfer(mabelsAccount, dollars300));

        assertAccountsKeepsInitialBalance(franciscosAccount, mabelsAccount);
    }
}
