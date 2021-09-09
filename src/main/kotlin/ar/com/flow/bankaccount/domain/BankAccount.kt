package ar.com.flow.bankaccount.domain

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.Money

interface BankAccount {
    val owner: Customer
    val currency: Currency
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