package com.emilianomenendez.veritran.bankaccount.money;

import com.emilianomenendez.veritran.NegativeAmountException;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import com.emilianomenendez.veritran.money.Number;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;

public class DollarsTest {
    @Test
    void givenAPositiveAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldBeCreated() {
        Dollars dollars = Dollars.amount(10);

        Assertions.assertEquals("USD", dollars.getCurrency());
        Assertions.assertEquals(10, dollars.getAmount());
    }

    @Test
    void givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        Assertions.assertThrows(NegativeAmountException.class, () -> Dollars.amount(-10));
    }

    @Test
    void given100USDWhenAdd10USDThenResultShould110USD() {
        Assertions.assertEquals(dollars110, dollars100.plus(dollars10));
    }

    @Test
    void given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.plus(dollars10);

        Assertions.assertEquals(Dollars.amount(100), dollars100);
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        Assertions.assertEquals(dollars90, dollars100.minus(dollars10));
    }

    @Test
    void given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        Assertions.assertThrows(InsufficientFundsException.class, () ->
                dollars100.minus(dollars200));
    }

    @Test
    void given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.minus(dollars10);

        Assertions.assertEquals(Dollars.amount(100), dollars100);
    }
}
