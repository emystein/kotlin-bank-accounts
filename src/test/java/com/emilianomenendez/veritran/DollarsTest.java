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
        assertEquals(TestObjects.dollars90, TestObjects.dollars100.minus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () ->
                TestObjects.dollars100.minus(TestObjects.dollars200));
    }

    @Test
    void given100USDWhenAdd10USDThenResultShouldEqualSum() {
        assertEquals(TestObjects.dollars110, TestObjects.dollars100.plus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        TestObjects.dollars100.plus(TestObjects.dollars10);

        assertEquals(Dollars.amount(100), TestObjects.dollars100);
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldEqualSubtraction() {
        assertEquals(TestObjects.dollars90, TestObjects.dollars100.minus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        TestObjects.dollars100.minus(TestObjects.dollars10);

        assertEquals(Dollars.amount(100), TestObjects.dollars100);
    }
}
