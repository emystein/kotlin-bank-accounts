package ar.com.flow.money

class Dollars private constructor(amount: Int) : Money("USD", amount) {
    companion object {
        @JvmStatic
        fun amount(amount: Int): Dollars {
            return Dollars(amount)
        }
    }
}