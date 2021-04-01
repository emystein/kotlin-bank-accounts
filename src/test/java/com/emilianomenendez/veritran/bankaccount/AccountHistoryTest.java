package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountHistoryTest {
    private AccountHistory history;
    private AccountMovement movement1 = new AccountMovement(now(), Dollars.amount(10));
    private AccountMovement movement2 = new AccountMovement(now(), Dollars.amount(20));

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
