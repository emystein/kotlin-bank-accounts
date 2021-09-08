package ar.com.flow.money

data class Money(val currency: String, val amount: Int) {
    init {
        require(amount >= 0) { "Amount must be non-negative: $amount" }
    }

    fun isGreaterThanOrEqual(other: Money): Boolean {
        return amount >= other.amount
    }

    fun isLessThan(other: Money): Boolean {
        return amount < other.amount
    }

    operator fun plus(amountToAdd: Money): Money {
        return Money(currency, amount + amountToAdd.amount)
    }

    operator fun minus(amountToSubtract: Money): Money {
        if (isLessThan(amountToSubtract)) {
            throw InsufficientFundsException()
        }
        return Money(currency, amount - amountToSubtract.amount)
    }

    companion object {
        fun zero(currency: String): Money {
            return Money(currency, 0)
        }
    }
}

object Dollars {
    fun amount(amount: Int): Money {
        return Money("USD", amount)
    }
}

object Pesos {
    fun amount(amount: Int): Money {
        return Money("ARS", amount)
    }
}
