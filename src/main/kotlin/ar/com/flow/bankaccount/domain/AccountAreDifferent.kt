package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.Precondition

class AccountAreDifferent(
    private val debitAccount: BankAccount,
    private val creditAccount: BankAccount
) : Precondition {

    override fun check() {
        if (debitAccount == creditAccount) {
            throw SameAccountException()
        }
    }
}