package ar.com.flow.bankaccount.balance

object PositiveBalance {
    fun of(currency: String, amount: Int): Balance {
        require(amount >= 0)
        return Balance(currency, amount)
    }
}