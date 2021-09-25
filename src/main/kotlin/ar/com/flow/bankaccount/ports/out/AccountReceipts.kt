package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.DifferentAccountException
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

interface AccountReceipts {
    val accountId: AccountId

    fun all(): List<Receipt>

    @Throws(DifferentAccountException::class)
    fun add(receipt: Receipt)
}