package ar.com.flow.bankaccount.domain.balance

import ar.com.flow.bankaccount.domain.Currency

object PositiveBalance {
    fun of(currency: Currency, amount: Int): Balance {
        require(amount >= 0)
        return Balance(currency, amount)
    }
}