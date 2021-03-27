package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.assertBalanceDecreasedBy;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.createCheckingAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;

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
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100);

        Dollars amountToWithdraw = dollars10;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
    }

    @Test
    void givenACheckingAccountWith100USDBalanceWhenWithdraw200USDThenBalanceShouldBeMinus200USD() {
        CheckingAccount account = createCheckingAccountFor(francisco, dollars100);

        Dollars amountToWithdraw = dollars200;

        account.withdraw(amountToWithdraw);

        assertBalanceDecreasedBy(account, amountToWithdraw);
    }
}
