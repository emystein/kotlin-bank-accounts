package com.emilianomenendez.veritran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DollarsTest {
    @Test
    void givenAPositiveAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldBeCreated() {
        Dollars dollars = Dollars.amount(10);

        assertEquals(10, dollars.getAmount());
    }

    @Test
    void givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        assertThrows(NegativeAmountException.class, () -> Dollars.amount(-10));
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        Dollars dollars100 = Dollars.amount(100);

        assertEquals(Dollars.amount(90), dollars100.minus(Dollars.amount(10)));
    }

    @Test
    void given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        Dollars dollars100 = Dollars.amount(100);
        Dollars dollars200 = Dollars.amount(200);

        assertThrows(InsufficientFundsException.class, () ->
                dollars100.minus(dollars200));
    }
}
