package com.emilianomenendez.veritran.bankaccount;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMovementTest {
    @Test
    void createAccountMovement() {
        LocalDateTime dateTime = now();

        AccountMovement movement = new AccountMovement(dateTime, dollars10);

        assertEquals(dateTime, movement.getDateTime());
        assertEquals(dollars10.getCurrency(), movement.getCurrency());
        assertEquals(dollars10, movement.getAmount());
    }
}