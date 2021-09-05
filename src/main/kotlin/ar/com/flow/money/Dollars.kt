package ar.com.flow.money

object Dollars {
    fun amount(amount: Int): Money {
        return Money("USD", amount)
    }
}