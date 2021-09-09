package ar.com.flow.bankaccount.domain.balance

import ar.com.flow.bankaccount.domain.Currency
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BalanceTest {
    @Test
    fun givenNegativeAmountWhenCreatePositiveBalanceThenItShouldNotBeCreated() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { PositiveBalance.of(Currency.USD, -10) }
    }

    @Test
    fun givenPositiveAmountWhenCreatePositiveBalanceThenItShouldHavePositiveAmount() {
        val balance = PositiveBalance.of(Currency.USD, 10)
        assertBalance(balance, Currency.USD, 10)
    }

    @Test
    fun givenPositiveAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = NegativeBalance.of(Currency.USD, 10)
        assertBalance(balance, Currency.USD, -10)
    }

    @Test
    fun givenNegativeAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = NegativeBalance.of(Currency.USD, -10)
        assertBalance(balance, Currency.USD, -10)
    }

    private fun assertBalance(balance: Balance, expectedCurrency: Currency, expectedAmount: Int) {
        Assertions.assertEquals(expectedCurrency, balance.currency)
        Assertions.assertEquals(expectedAmount, balance.amount)
    }
}