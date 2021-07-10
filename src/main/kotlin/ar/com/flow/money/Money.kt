package ar.com.flow.money

open class Money(val currency: String, val amount: Int) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Money

        if (currency != other.currency) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + amount
        return result
    }
}