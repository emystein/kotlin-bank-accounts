package ar.com.flow.bankaccount.transaction.receipt

import ar.com.flow.money.Money

interface ReceiptPrint {
    fun print(amount: Money): Receipt
}