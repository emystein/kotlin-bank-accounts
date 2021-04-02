package com.emilianomenendez.veritran.bankaccount.money;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DollarsTest {
    @Test
    void givenAPositiveAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldBeCreated() {
        var dollars = Dollars.amount(10);

        assertEquals("USD", dollars.getCurrency());
        assertEquals(10, dollars.getAmount());
    }

    @Test
    void givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        assertThrows(IllegalArgumentException.class, () -> Dollars.amount(-10));
    }

    @Test
    void given100USDWhenAdd10USDThenResultShould110USD() {
        assertEquals(dollars110, dollars100.plus(dollars10));
    }

    @Test
    void given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.plus(dollars10);

        assertEquals(Dollars.amount(100), dollars100);
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        assertEquals(dollars90, dollars100.minus(dollars10));
    }

    @Test
    void given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () -> dollars100.minus(dollars200));
    }

    @Test
    void given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.minus(dollars10);

        assertEquals(Dollars.amount(100), dollars100);
    }
}
