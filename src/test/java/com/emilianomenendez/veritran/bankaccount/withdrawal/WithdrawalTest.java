package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.SavingsAccount;
import com.emilianomenendez.veritran.bankaccount.SignedMoney;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import com.emilianomenendez.veritran.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.createSavingsAccountFor;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WithdrawalTest {
    private Customer francisco;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
    }

    @Test
    void givenSavingsAccountWith100USDBalanceWhenExecute10USDWithdrawalThenItShouldCalculateNewBalance() {
        SavingsAccount account = createSavingsAccountFor(francisco, dollars100);

        Balance balanceAfter = Withdrawal.from(account).amount(dollars10).execute();

        assertEquals(Balance.create(dollars90), balanceAfter);
    }

    @Test
    void givenSavingsAccountWith100USDBalanceWhenCreate200USDWithdrawalThenItShouldBeRejected() {
        SavingsAccount account = createSavingsAccountFor(francisco, dollars100);

        assertThrows(InsufficientFundsException.class, () -> Withdrawal.from(account).amount(dollars200).execute());
    }
}
