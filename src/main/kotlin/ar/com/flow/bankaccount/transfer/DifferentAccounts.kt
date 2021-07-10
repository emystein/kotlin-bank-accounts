package ar.com.flow.bankaccount.transfer

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.transaction.Precondition

class DifferentAccounts(
    private val debitAccount: BankAccount,
    private val creditAccount: BankAccount
) : Precondition {

    override fun check() {
        if (debitAccount == creditAccount) {
            throw SameAccountException()
        }
    }
}