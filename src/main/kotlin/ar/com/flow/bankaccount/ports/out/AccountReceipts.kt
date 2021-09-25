package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

interface AccountReceipts {
    val accountId: AccountId
    fun all(): List<Receipt>
    // TODO: add precondition of receipt account owner and currency must match this instance accountOwner and currency
    fun add(receipt: Receipt)
}