package com.emilianomenendez.veritran.bankaccount;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {
    @Test
    void positiveBalanceShouldNotAcceptNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PositiveBalance("USD", -10));
    }

    @Test
    void positiveBalanceShouldIncludePositiveSignAndCurrencyAndAmount() {
        PositiveBalance balance = new PositiveBalance("USD", 10);

        assertEquals("+", balance.getSign());
        assertEquals("USD", balance.getCurrency());
        assertEquals(10, balance.getAmount());
    }

    @Test
    void negativeBalanceShouldIncludeNegativeSignAndCurrencyAndAmount() {
        NegativeBalance balance = new NegativeBalance("USD", 10);

        assertEquals("-", balance.getSign());
        assertEquals("USD", balance.getCurrency());
        assertEquals(-10, balance.getAmount());
    }

    @Test
    void PositiveBalance100UsdShouldBeGreaterThanPositiveBalance10USD() {
        PositiveBalance balance100 = new PositiveBalance("USD", 100);
        PositiveBalance balance10 = new PositiveBalance("USD", 10);

        assertTrue(balance100.isGreaterThanOrEqual(balance10));
    }

    @Test
    void PositiveBalance100UsdShouldBeGreaterThanNegativeBalance10USD() {
        PositiveBalance balance100 = new PositiveBalance("USD", 100);
        NegativeBalance balanceMinus10 = new NegativeBalance("USD", 10);

        assertTrue(balance100.isGreaterThanOrEqual(balanceMinus10));
    }

    @Test
    void NegativeBalance10UsdShouldBeLowerThanPositiveBalance10USD() {
        NegativeBalance balanceMinus10 = new NegativeBalance("USD", 10);
        PositiveBalance balance10 = new PositiveBalance("USD", 10);

        assertTrue(balanceMinus10.isLessThan(balance10));
    }

    @Test
    void NegativeBalance100UsdShouldBeLowerThanNegativeBalance10USD() {
        NegativeBalance balanceMinus100 = new NegativeBalance("USD", 100);
        NegativeBalance balanceMinus10 = new NegativeBalance("USD", 10);

        assertTrue(balanceMinus100.isLessThan(balanceMinus10));
    }
}