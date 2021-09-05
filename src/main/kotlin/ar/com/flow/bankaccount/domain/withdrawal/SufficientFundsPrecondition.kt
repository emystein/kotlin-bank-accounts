package ar.com.flow.bankaccount.domain.withdrawal

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.transaction.Precondition
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.Money

class SufficientFundsPrecondition(private val account: BankAccount, private val amount: Money) : Precondition {
    override fun check() {
        if (!account.withdrawalLimitSupports(amount)) {
            throw InsufficientFundsException()
        }
    }
}