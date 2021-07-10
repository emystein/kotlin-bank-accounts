package ar.com.flow.bankaccount.balance

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BalanceTest {
    @Test
    fun givenNegativeAmountWhenCreatePositiveBalanceThenItShouldNotBeCreated() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { PositiveBalance.of("USD", -10) }
    }

    @Test
    fun givenPositiveAmountWhenCreatePositiveBalanceThenItShouldHavePositiveAmount() {
        val balance = PositiveBalance.of("USD", 10)
        assertBalance(balance, "USD", 10)
    }

    @Test
    fun givenPositiveAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = NegativeBalance.of("USD", 10)
        assertBalance(balance, "USD", -10)
    }

    @Test
    fun givenNegativeAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = NegativeBalance.of("USD", -10)
        assertBalance(balance, "USD", -10)
    }

    private fun assertBalance(balance: Balance, expectedCurrency: String, expectedAmount: Int) {
        Assertions.assertEquals(expectedCurrency, balance.currency)
        Assertions.assertEquals(expectedAmount, balance.amount)
    }
}