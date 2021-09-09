package ar.com.flow.bankaccount.domain.balance

import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.money.Money

class Balance(val currency: Currency, val amount: Int) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Balance

        if (currency != other.currency) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + amount
        return result
    }

    override fun toString(): String {
        return "Balance(currency='$currency', amount=$amount)"
    }

    companion object {
        @JvmStatic
        fun zero(currency: Currency): Balance {
            return create(currency, 0)
        }

        fun create(initialBalance: Money): Balance {
            return create(initialBalance.currency, initialBalance.amount)
        }

        fun create(currency: Currency, amount: Int): Balance {
            return if (amount >= 0) {
                PositiveBalance.of(currency, amount)
            } else {
                NegativeBalance.of(currency, amount)
            }
        }

        @JvmStatic
        fun positive(amount: Money): Balance {
            return PositiveBalance.of(amount.currency, amount.amount)
        }

        @JvmStatic
        fun negative(amount: Money): Balance {
            return NegativeBalance.of(amount.currency, amount.amount)
        }
    }
}