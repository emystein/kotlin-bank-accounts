package ar.com.flow.bankaccount.domain

import ar.com.flow.money.Money
import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show

fun Assert<BankAccount>.keepsInitialBalance() = given { actual ->
    if (actual.balance == actual.initialBalance) return
    expected("balance:${show(actual.initialBalance)} but was balance:${show(actual.balance)}")
}

fun Assert<BankAccount>.increasedFunds(amount: Money) = given { actual ->
    if (actual.balance == actual.previousBalance.plus(amount)) return
    expected("balance:${show(actual.previousBalance.plus(amount))} but was balance:${show(actual.balance)}")
}

fun Assert<BankAccount>.decreasedFunds(amount: Money) = given { actual ->
    if (actual.balance == actual.previousBalance.minus(amount)) return
    expected("balance:${show(actual.previousBalance.minus(amount))} but was balance:${show(actual.balance)}")
}

fun Assert<BankAccount>.hasNegativeBalance(amount: Money) = given { actual ->
    if (actual.balance == Balance.negative(amount)) return
    expected("balance should be negative")
}

