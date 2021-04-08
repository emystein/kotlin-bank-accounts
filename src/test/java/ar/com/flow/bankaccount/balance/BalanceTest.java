package ar.com.flow.bankaccount.balance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceTest {
    @Test
    void givenNegativeAmountWhenCreatePositiveBalanceThenItShouldNotBeCreated() {
        assertThrows(IllegalArgumentException.class, () -> new PositiveBalance("USD", -10));
    }

    @Test
    void givenPositiveAmountWhenCreatePositiveBalanceThenItShouldHavePositiveAmount() {
        var balance = new PositiveBalance("USD", 10);

        assertBalance(balance, "USD", 10);
    }

    @Test
    void givenPositiveAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        var balance = new NegativeBalance("USD", 10);

        assertBalance(balance, "USD", -10);
    }

    @Test
    void givenNegativeAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        var balance = new NegativeBalance("USD", -10);

        assertBalance(balance, "USD", -10);
    }

    private void assertBalance(Balance balance, String expectedCurrency, int expectedAmount) {
        assertEquals(expectedCurrency, balance.getCurrency());
        assertEquals(expectedAmount, balance.getAmount());
    }
}