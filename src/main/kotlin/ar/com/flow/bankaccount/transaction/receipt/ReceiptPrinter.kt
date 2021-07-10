package ar.com.flow.bankaccount.transaction.receipt

import ar.com.flow.money.Money

interface ReceiptPrinter {
    fun print(amount: Money): Receipt
    fun scratch(amount: Money): Receipt
}