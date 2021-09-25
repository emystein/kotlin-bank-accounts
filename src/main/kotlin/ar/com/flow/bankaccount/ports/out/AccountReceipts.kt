package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

interface AccountReceipts {
    fun all(): List<Receipt>
    fun add(receipt: Receipt)
}