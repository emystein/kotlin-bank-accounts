package ar.com.flow.money

import ar.com.flow.money.Dollars.amount

object TestMoney {
    val dollars10: Money = amount(10)
    val dollars20: Money = amount(20)
    val dollars90: Money = amount(90)
    val dollars100: Money = amount(100)
    val dollars110: Money = amount(110)
    val dollars200: Money = amount(200)
    val dollars300: Money = amount(300)

    val ars100 = Money("ARS", 100)
}