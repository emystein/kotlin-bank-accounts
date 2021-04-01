package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountHistoryTest {
    @Test
    void givenAnAccountMovementWhenAddTheAccountMovementToTheAccountHistoryThenTheAccountHistoryShouldContainTheAccountMovement() {
        AccountMovement movement = new AccountMovement(LocalDateTime.now(), Dollars.amount(10));

        AccountHistory accountHistory = new InMemoryAccountHistory();

        accountHistory.add(movement);

        assertTrue(accountHistory.contains(movement));
    }

    @Test
    void givenAccountMovement1AndAccountMovement2WhenAddTheAccountMovementsToTheAccountHistoryThenTheAccountHistoryShouldContainTheAccountMovementsInOrderOfAddition() {
        AccountMovement movement1 = new AccountMovement(LocalDateTime.now(), Dollars.amount(10));
        AccountMovement movement2 = new AccountMovement(LocalDateTime.now(), Dollars.amount(20));

        AccountHistory accountHistory = new InMemoryAccountHistory();

        accountHistory.add(movement1);
        accountHistory.add(movement2);

        assertTrue(accountHistory.containsInOrder(movement1, movement2));
    }
}
