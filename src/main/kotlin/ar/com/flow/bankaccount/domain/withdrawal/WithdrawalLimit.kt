package ar.com.flow.bankaccount.domain.withdrawal

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

interface WithdrawalLimit {
    fun accepts(account: BankAccount, amount: Money): Boolean
}