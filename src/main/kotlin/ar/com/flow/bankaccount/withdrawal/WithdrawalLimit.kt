package ar.com.flow.bankaccount.withdrawal

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.money.Money

interface WithdrawalLimit {
    fun accepts(account: BankAccount, amount: Money): Boolean
}