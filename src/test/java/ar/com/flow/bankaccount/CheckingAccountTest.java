package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.BankAccountAssert.assertThat;
import static ar.com.flow.bankaccount.TestObjects.*;
import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckingAccountTest {
    private BankAccount franciscosAccount;
    private BankAccount mabelsAccount;

    @BeforeEach
    void setUp() {
        franciscosAccount = createCheckingAccountFor(francisco, dollars100, minusDollars100Limit);
        mabelsAccount = createCheckingAccountFor(mabel, dollars100, minusDollars100Limit);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        franciscosAccount.withdraw(dollars10);

        assertThat(franciscosAccount).decreasedFunds(dollars10);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw110USDThenBalanceShouldBeMinus10USD() {
        franciscosAccount.withdraw(dollars110);

        assertEquals(Balance.negative(dollars10), franciscosAccount.getBalance());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw300USDThenWithdrawalShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars300));

        assertThat(franciscosAccount).keepsInitialBalance();
    }


    @Test
    void givenADebitWith100USDBalanceWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars10);

        assertThat(franciscosAccount).decreasedFunds(dollars10);
        assertThat(mabelsAccount).increasedFunds(dollars10);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer110USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars110);

        assertThat(franciscosAccount).decreasedFunds(dollars110);
        assertThat(mabelsAccount).increasedFunds(dollars110);
    }

    @Test
    void givenADebitWith100USDBalanceAndWithdrawLimitMinus100USDWhenTransfer300USDThenTheMoneyShouldNotBeTransferred() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.transfer(mabelsAccount, dollars300));

        assertThat(franciscosAccount).keepsInitialBalance();
        assertThat(mabelsAccount).keepsInitialBalance();
    }
}
