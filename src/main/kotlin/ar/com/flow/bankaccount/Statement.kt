package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import java.util.*

interface Statement {
    fun total(): Int
    fun first(): Optional<Receipt>
    fun last(): Optional<Receipt>
    fun add(receipt: Receipt)
    operator fun contains(receipt: Receipt): Boolean
    fun containsInOrder(vararg records: Receipt): Boolean
    val currentBalance: Optional<Balance>
    val previousBalance: Optional<Balance>
    val initialBalance: Optional<Balance>
}