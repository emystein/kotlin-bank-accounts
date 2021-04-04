package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.*;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionHistoryTest {
    private TransactionHistory history;

    @BeforeEach
    void setUp() {
        history = new InMemoryTransactionHistory();
    }

    @Test
    void givenATransactionRecordWhenAddItToTransactionHistoryThenItShouldContainTheTransactionRecord() {
        history.add(dollars10Record);

        assertTrue(history.contains(dollars10Record));
    }

    @Test
    void givenTwoTransactionRecordsWhenAddThemToTransactionHistoryThenItShouldContainThemInOrderOfAddition() {
        history.add(dollars10Record);
        history.add(dollars20Record);

        assertTrue(history.containsInOrder(dollars10Record, dollars20Record));
    }

    @Test
    void givenAUSD10CreditAndAUSD20CreditWhenAddThemToTransactionHistoryThenTransactionHistorySumShouldBeUSD30() {
        history.add(dollars10Record);
        history.add(dollars20Record);

        assertEquals(Balance.positive(Dollars.amount(30)), history.sum());
    }

    @Test
    void givenAUSD10CreditAndAUSD20DebitWhenAddThemToTransactionHistoryThenTransactionHistorySumShouldBeMinusUSD10() {
        history.add(dollars10Record);
        history.add(minusDollars20Record);

        assertEquals(Balance.negative(dollars10), history.sum());
    }
}
