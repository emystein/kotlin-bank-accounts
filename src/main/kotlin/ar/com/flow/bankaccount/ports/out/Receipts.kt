package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

interface Receipts {
    fun all(accountOwner: Customer, currency: Currency): List<Receipt>
    fun add(receipt: Receipt)
}