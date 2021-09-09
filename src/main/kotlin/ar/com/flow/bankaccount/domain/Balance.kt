package ar.com.flow.bankaccount.domain

import ar.com.flow.money.Money
import kotlin.math.min

data class Balance(val currency: Currency, val amount: Int) {
    fun isGreaterThanOrEqual(other: Money): Boolean {
        return amount >= other.amount
    }

    fun isGreaterThanOrEqual(other: Balance): Boolean {
        return amount >= other.amount
    }

    operator fun plus(other: Balance): Balance {
        return create(currency, amount + other.amount)
    }

    operator fun plus(other: Money): Balance {
        return create(currency, amount + other.amount)
    }

    operator fun minus(other: Money): Balance {
        return create(currency, amount - other.amount)
    }

    override fun toString(): String {
        return "Balance(currency='$currency', amount=$amount)"
    }

    companion object {
        fun zero(currency: Currency): Balance {
            return create(currency, 0)
        }

        fun create(initialBalance: Money): Balance {
            return create(initialBalance.currency, initialBalance.amount)
        }

        fun create(currency: Currency, amount: Int): Balance {
            return Balance(currency, amount)
        }

        fun positive(amount: Money): Balance {
            require(amount.amount >= 0)
            return Balance(amount.currency, amount.amount)
        }

        fun negative(amount: Money): Balance {
            return Balance(amount.currency, min(-amount.amount, amount.amount))
        }
    }
}
