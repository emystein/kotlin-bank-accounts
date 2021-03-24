package com.emilianomenendez.veritran.bankaccount.money;

import com.emilianomenendez.veritran.NegativeAmountException;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DollarsTest {
    @Test
    void givenAPositiveAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldBeCreated() {
        Dollars dollars = Dollars.amount(10);

        Assertions.assertEquals(10, dollars.getAmount());
    }

    @Test
    void givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        Assertions.assertThrows(NegativeAmountException.class, () -> Dollars.amount(-10));
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        Assertions.assertEquals(TestObjects.dollars90, TestObjects.dollars100.minus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        Assertions.assertThrows(InsufficientFundsException.class, () ->
                TestObjects.dollars100.minus(TestObjects.dollars200));
    }

    @Test
    void given100USDWhenAdd10USDThenResultShouldEqualSum() {
        Assertions.assertEquals(TestObjects.dollars110, TestObjects.dollars100.plus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        TestObjects.dollars100.plus(TestObjects.dollars10);

        Assertions.assertEquals(Dollars.amount(100), TestObjects.dollars100);
    }

    @Test
    void given100USDWhenSubtract10USDThenResultShouldEqualSubtraction() {
        Assertions.assertEquals(TestObjects.dollars90, TestObjects.dollars100.minus(TestObjects.dollars10));
    }

    @Test
    void given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        TestObjects.dollars100.minus(TestObjects.dollars10);

        Assertions.assertEquals(Dollars.amount(100), TestObjects.dollars100);
    }
}
