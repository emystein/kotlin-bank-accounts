package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.Statement
import ar.com.flow.money.Money

interface BankAccount {
    val currency: String
    val initialBalance: Balance
    val balance: Balance
    val previousBalance: Balance
    fun deposit(amountToDeposit: Money)
    fun withdraw(amountToWithdraw: Money)
    fun transfer(amountToTransfer: Money, creditAccount: BankAccount)
    fun addReceipt(receipt: Receipt)
    val statement: Statement
    fun withdrawalLimitSupports(amount: Money): Boolean
}