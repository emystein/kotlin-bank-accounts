package ar.com.flow.bankaccount.domain.balance

import ar.com.flow.bankaccount.domain.Currency
import kotlin.math.min

object NegativeBalance {
    fun of(currency: Currency, amount: Int): Balance {
        return Balance(currency, min(-amount, amount))
    }
}
