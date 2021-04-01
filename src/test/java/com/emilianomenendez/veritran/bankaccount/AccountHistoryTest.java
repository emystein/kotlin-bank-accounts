package com.emilianomenendez.veritran.bankaccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.movement1;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.movement2;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountHistoryTest {
    private AccountHistory history;

    @BeforeEach
    void setUp() {
        history = new InMemoryAccountHistory();
    }

    @Test
    void givenAnAccountMovementWhenAddTheAccountMovementToTheAccountHistoryThenTheAccountHistoryShouldContainTheAccountMovement() {
        history.add(movement1);

        assertTrue(history.contains(movement1));
    }

    @Test
    void givenAccountMovement1AndAccountMovement2WhenAddTheAccountMovementsToTheAccountHistoryThenTheAccountHistoryShouldContainTheAccountMovementsInOrderOfAddition() {
        history.add(movement1);
        history.add(movement2);

        assertTrue(history.containsInOrder(movement1, movement2));
    }
}
