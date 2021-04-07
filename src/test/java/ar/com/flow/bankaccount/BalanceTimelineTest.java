package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

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
