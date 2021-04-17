package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Receipt;
import ar.com.flow.money.Dollars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor;
import static ar.com.flow.bankaccount.TestObjects.francisco;
import static ar.com.flow.money.TestObjects.*;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatementTest {
    private BankAccount franciscosAccount;
    private Statement statement;
    private Receipt dollars10Record;
    private Receipt dollars20Record;
    private Receipt minusDollars20Record;

    @BeforeEach
    void setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100);
        dollars10Record = Receipt.credit(franciscosAccount, Action.Deposit, dollars10);
        dollars20Record = Receipt.credit(franciscosAccount, Action.Deposit, dollars20);
        minusDollars20Record = Receipt.debit(franciscosAccount, Action.Withdrawal, dollars20);
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
