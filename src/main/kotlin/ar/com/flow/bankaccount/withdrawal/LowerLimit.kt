package ar.com.flow.bankaccount.withdrawal

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.money.Money

class LowerLimit(private val limit: Balance) : WithdrawalLimit {
    override fun accepts(account: BankAccount, amount: Money): Boolean {
        val previewBalanceAfter = account.balance.minus(amount)
        return previewBalanceAfter.isGreaterThanOrEqual(limit)
    }
}