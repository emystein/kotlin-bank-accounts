package ar.com.flow.bankaccount.domain.withdrawal

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.money.Money

class LowerLimit(private val limit: Balance) : WithdrawalLimit {
    override fun accepts(account: BankAccount, amount: Money): Boolean {
        val previewBalanceAfter = account.balance.minus(amount)
        return previewBalanceAfter.isGreaterThanOrEqual(limit)
    }
}