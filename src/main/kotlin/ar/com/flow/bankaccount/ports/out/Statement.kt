package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*
import kotlin.math.max

interface Statement {
    val currency: Currency
    fun all(): Collection<Receipt>
    fun add(receipt: Receipt)

    fun count(): Int {
        return all().size
    }

    fun first(): Optional<Receipt> {
        return Optional.ofNullable(all().firstOrNull())
    }

    fun last(): Optional<Receipt> {
        return Optional.ofNullable(all().lastOrNull())
    }

    fun contains(receipt: Receipt): Boolean {
        return containsInOrder(receipt)
    }

    fun containsInOrder(vararg records: Receipt): Boolean {
        val expected = listOf(*records)

        val stored = all().filter { receipt -> expected.contains(receipt) }

        return (stored == expected)
    }

    fun sum(numberOfTransactions: Int): Balance {
        return all().take(max(numberOfTransactions, 0))
            .map { receipt -> receipt.amount }
            .fold(Balance.zero(currency)) { balance, receiptAmount -> balance.plus(receiptAmount) }
    }
}