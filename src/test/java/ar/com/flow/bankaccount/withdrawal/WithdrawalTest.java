package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.Balance;
import ar.com.flow.bankaccount.TestObjects;
import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.money.TestObjects.*;
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
        var account = TestObjects.createSavingsAccountFor(francisco, dollars100);

        var transactionRecord = Withdrawal.from(account).amount(dollars10).execute();

        assertEquals(Balance.negative(dollars10), transactionRecord.getBalance());
    }

    @Test
    void givenSavingsAccountWith100USDBalanceWhenCreate200USDWithdrawalThenItShouldBeRejected() {
        var account = TestObjects.createSavingsAccountFor(francisco, dollars100);

        assertThrows(InsufficientFundsException.class, () -> Withdrawal.from(account).amount(dollars200).execute());
    }
}
