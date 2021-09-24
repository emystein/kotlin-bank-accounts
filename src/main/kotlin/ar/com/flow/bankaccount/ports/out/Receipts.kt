package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

interface Receipts {
    fun all(accountId: AccountId): List<Receipt>
    fun add(receipt: Receipt)
}