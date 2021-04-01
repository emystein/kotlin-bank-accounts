package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars20;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountHistoryTest {
    private AccountHistory history;
    private AccountMovement movement1 = new AccountMovement(now(), dollars10.getCurrency(), dollars10);
    private AccountMovement movement2 = new AccountMovement(now(), dollars20.getCurrency(), dollars20);

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
