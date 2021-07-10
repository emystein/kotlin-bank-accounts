package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import java.util.*

interface Statement {
    val currency: String
    fun total(): Int
    fun first(): Optional<Receipt>
    fun last(): Optional<Receipt>
    fun add(receipt: Receipt)
    operator fun contains(receipt: Receipt): Boolean
    fun containsInOrder(vararg records: Receipt): Boolean
    val currentBalance: Balance
    val previousBalance: Balance
    val initialBalance: Balance
    fun sum(numberOfTransactions: Int): Balance
}