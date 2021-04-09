package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Dollars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static ar.com.flow.bankaccount.TestObjects.*;
import static ar.com.flow.money.TestObjects.dollars10;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatementTest {
    private Statement history;

    @BeforeEach
    void setUp() {
        history = new InMemoryStatement();
    }

    @Test
    void givenTransactionHistoryInitialStateWhenGetFirstElementThenItShouldBeNone() {
        assertEquals(Optional.empty(), history.first());
    }

    @Test
    void givenTransactionHistoryInitialStateWhenGetSumThenItShouldReturnEmpty() {
        assertEquals(Optional.empty(), history.getInitialBalance());
        assertEquals(Optional.empty(), history.getCurrentBalance());
        assertEquals(Optional.empty(), history.getPreviousBalance());
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

        assertEquals(Optional.of(Balance.positive(Dollars.amount(10))), history.getInitialBalance());
        assertEquals(Optional.of(Balance.positive(Dollars.amount(30))), history.getCurrentBalance());
        assertEquals(Optional.of(Balance.positive(Dollars.amount(10))), history.getPreviousBalance());
    }

    @Test
    void givenAUSD10CreditAndAUSD20DebitWhenAddThemToTransactionHistoryThenTransactionHistorySumShouldBeMinusUSD10() {
        history.add(dollars10Record);
        history.add(minusDollars20Record);

        assertEquals(Optional.of(Balance.negative(dollars10)), history.getCurrentBalance());
    }
}
