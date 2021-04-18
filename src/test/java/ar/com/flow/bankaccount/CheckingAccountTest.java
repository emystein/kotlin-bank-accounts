package ar.com.flow.bankaccount;

import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.BankAccountAssert.assertThat;
import static ar.com.flow.bankaccount.TestObjects.*;
import static ar.com.flow.money.TestObjects.*;
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
    void withdrawalDecrementsFunds() {
        franciscosAccount.withdraw(dollars10);

        assertThat(franciscosAccount).decreasedFunds(dollars10);
    }

    @Test
    void withdrawAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        franciscosAccount.withdraw(dollars110);

        assertThat(franciscosAccount).hasNegativeBalance(dollars10);
    }

    @Test
    void withdrawalAmountCanNotExceedWithdrawalLimit() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars300));

        assertThat(franciscosAccount).keepsInitialBalance();
    }

    @Test
    void transferAmountLessThanAvailableFunds() {
        franciscosAccount.transfer(dollars10, mabelsAccount);

        assertThat(franciscosAccount).decreasedFunds(dollars10);
        assertThat(mabelsAccount).increasedFunds(dollars10);
    }

    @Test
    void transferAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        franciscosAccount.transfer(dollars110, mabelsAccount);

        assertThat(franciscosAccount).decreasedFunds(dollars110);
        assertThat(mabelsAccount).increasedFunds(dollars110);
    }

    @Test
    void transferAmountCanNotExceedWithdrawalLimit() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.transfer(dollars300, mabelsAccount));

        assertThat(franciscosAccount).keepsInitialBalance();
        assertThat(mabelsAccount).keepsInitialBalance();
    }
}
