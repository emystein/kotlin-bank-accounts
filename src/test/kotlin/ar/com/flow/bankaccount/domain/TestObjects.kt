package ar.com.flow.bankaccount.domain

import ar.com.flow.Customer.Companion.named
import ar.com.flow.bankaccount.domain.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.withdrawal.LowerLimit
import ar.com.flow.money.TestMoney

object TestObjects {
    var daniel = named("Daniel")
    var mabel = named("Mabel")

    var minusDollars100Limit = LowerLimit(negative(TestMoney.dollars100))
}
