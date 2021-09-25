package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SameAccountException
import ar.com.flow.bankaccount.domain.transaction.Precondition

class DifferentAccountsPrecondition(
    private val debitAccount: BankAccount,
    private val creditAccount: BankAccount
) : Precondition {

    override fun check() {
        if (debitAccount == creditAccount) {
            throw SameAccountException()
        }
    }
}