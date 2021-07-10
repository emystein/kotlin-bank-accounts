package ar.com.flow.bankaccount.balance

import kotlin.math.min

object NegativeBalance {
    fun of(currency: String, amount: Int): Balance {
        return Balance(currency, min(-amount, amount))
    }
}
