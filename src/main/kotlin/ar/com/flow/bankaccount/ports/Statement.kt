package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*

interface Statement {
    val currency: String
    fun total(): Int
    fun first(): Optional<Receipt>
    fun last(): Optional<Receipt>
    fun add(receipt: Receipt)
    operator fun contains(receipt: Receipt): Boolean
    fun containsInOrder(vararg records: Receipt): Boolean
    fun getInitialBalance(): Balance
    fun getCurrentBalance(): Balance
    fun getPreviousBalance(): Balance
    fun sum(numberOfTransactions: Int): Balance
}