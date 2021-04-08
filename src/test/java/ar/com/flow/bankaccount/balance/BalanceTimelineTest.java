package ar.com.flow.bankaccount.balance;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.TestObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BalanceTimelineTest {
    private Customer francisco;
    private BankAccount franciscosAccount;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        franciscosAccount = TestObjects.createSavingsAccountFor(francisco, dollars100);
    }

    @Test
    void givenADepositWhenGetBalanceTimelineSnapshotThenItShouldContainTheDeposit() {
        franciscosAccount.deposit(dollars10);

        var balanceTimeline = franciscosAccount.getBalanceTimeline();

        assertFalse(balanceTimeline.isEmpty());
        assertEquals(Balance.create(dollars100), balanceTimeline.initialSnapshot());
        assertEquals(Balance.create(dollars110), balanceTimeline.currentSnapshot());
        assertEquals(Balance.create(dollars100), balanceTimeline.previousSnapshot());
    }
}
