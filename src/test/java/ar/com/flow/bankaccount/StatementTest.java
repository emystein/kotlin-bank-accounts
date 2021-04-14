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
    private Statement statement;

    @BeforeEach
    void setUp() {
        statement = new InMemoryStatement();
    }

    @Test
    void newStatementIsEmpty() {
        assertEquals(Optional.empty(), statement.first());
        assertEquals(Optional.empty(), statement.getInitialBalance());
        assertEquals(Optional.empty(), statement.getCurrentBalance());
        assertEquals(Optional.empty(), statement.getPreviousBalance());
    }

    @Test
    void statementContainsTransactionRecordAdded() {
        statement.add(dollars10Record);

        assertTrue(statement.contains(dollars10Record));
    }

    @Test
    void statementStoresTransactionRecordsInOrderOfAddition() {
        statement.add(dollars10Record);
        statement.add(dollars20Record);

        assertTrue(statement.containsInOrder(dollars10Record, dollars20Record));
    }

    @Test
    void statementUpdatesBalance() {
        statement.add(dollars10Record);
        statement.add(dollars20Record);

        assertEquals(Optional.of(Balance.positive(Dollars.amount(10))), statement.getInitialBalance());
        assertEquals(Optional.of(Balance.positive(Dollars.amount(30))), statement.getCurrentBalance());
        assertEquals(Optional.of(Balance.positive(Dollars.amount(10))), statement.getPreviousBalance());
    }

    @Test
    void balanceSumsUpCreditAndDebitTransactionRecords() {
        statement.add(dollars10Record);
        statement.add(minusDollars20Record);

        assertEquals(Optional.of(Balance.negative(dollars10)), statement.getCurrentBalance());
    }
}
