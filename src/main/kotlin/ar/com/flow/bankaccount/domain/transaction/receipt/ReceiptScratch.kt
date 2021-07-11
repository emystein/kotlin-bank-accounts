package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.money.Money

interface ReceiptScratch {
    fun print(amount: Money): Receipt
}