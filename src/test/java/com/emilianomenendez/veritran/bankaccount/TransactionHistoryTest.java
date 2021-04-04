package com.emilianomenendez.veritran.bankaccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.transactionRecord1;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.transactionRecord2;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionHistoryTest {
    private TransactionHistory history;

    @BeforeEach
    void setUp() {
        history = new InMemoryTransactionHistory();
    }

    @Test
    void givenATransactionRecordWhenAddItToTransactionHistoryThenItShouldContainTheTransactionRecord() {
        history.add(transactionRecord1);

        assertTrue(history.contains(transactionRecord1));
    }

    @Test
    void givenTwoTransactionRecordsWhenAddThemToTransactionHistoryThenItShouldContainThemInOrderOfAddition() {
        history.add(transactionRecord1);
        history.add(transactionRecord2);

        assertTrue(history.containsInOrder(transactionRecord1, transactionRecord2));
    }
}
