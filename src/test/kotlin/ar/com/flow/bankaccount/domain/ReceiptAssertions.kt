package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.receipt.FundsMovement
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.money.Money
import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show

fun Assert<Receipt>.isDebit() = given { actual ->
    if (actual.movement == FundsMovement.Debit) return
    expected("Movement should be Debit")
}

fun Assert<Receipt>.isCredit() = given { actual ->
    if (actual.movement == FundsMovement.Credit) return
    expected("Movement should be Credit")
}

fun Assert<Receipt>.hasPositiveBalance(amount: Money) = given { actual ->
    if (actual.amount == Balance.positive(amount)) return
    expected("Receipt amount should be positive")
}

fun Assert<Receipt>.hasNegativeBalance(amount: Money) = given { actual ->
    if (actual.amount == Balance.negative(amount)) return
    expected("Receipt amount should be negative")
}

fun Assert<Receipt>.hasCreditAccount(expectedAccount: BankAccount) = given { actual ->
    if (actual.accountId == expectedAccount.id) return
    expected("Credit Account:${show(expectedAccount.id)} but was Credit Account:${show(actual.accountId)}")
}
