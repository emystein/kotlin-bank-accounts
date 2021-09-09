package ar.com.flow.bankaccount.domain.balance

import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.money.TestMoney.dollars10
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BalanceTest {
    @Test
    fun givenPositiveAmountWhenCreatePositiveBalanceThenItShouldHavePositiveAmount() {
        val balance = Balance.positive(dollars10)
        assertBalance(balance, Currency.USD, 10)
    }

    @Test
    fun givenPositiveAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = Balance.negative(dollars10)
        assertBalance(balance, Currency.USD, -10)
    }

    @Test
    fun givenNegativeAmountWhenCreateNegativeBalanceThenItShouldHaveNegativeAmount() {
        val balance = Balance.negative(dollars10)
        assertBalance(balance, Currency.USD, -10)
    }

    private fun assertBalance(balance: Balance, expectedCurrency: Currency, expectedAmount: Int) {
        Assertions.assertEquals(expectedCurrency, balance.currency)
        Assertions.assertEquals(expectedAmount, balance.amount)
    }
}