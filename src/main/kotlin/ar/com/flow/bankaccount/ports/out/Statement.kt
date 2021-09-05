package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*

interface Statement {
    val currency: String
    fun all(): Collection<Receipt>
    fun count(): Int
    fun first(): Optional<Receipt>
    fun last(): Optional<Receipt>
    fun add(receipt: Receipt)
    operator fun contains(receipt: Receipt): Boolean
    fun containsInOrder(vararg records: Receipt): Boolean
    fun zeroBalance() = Balance.zero(currency)
    fun getInitialBalance(): Balance
    fun getCurrentBalance(): Balance
    fun getPreviousBalance(): Balance
    fun sum(numberOfTransactions: Int): Balance
    fun clear()
}