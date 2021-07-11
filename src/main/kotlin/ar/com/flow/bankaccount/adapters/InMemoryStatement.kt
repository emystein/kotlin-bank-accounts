package ar.com.flow.bankaccount.adapters

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.Statement
import java.util.*
import kotlin.math.max

class InMemoryStatement(override val currency: String) : Statement {
    private val history: MutableList<Receipt> = ArrayList()

    override fun total(): Int {
        return history.size
    }

    override fun first(): Optional<Receipt> {
        return Optional.ofNullable(history.firstOrNull())
    }

    override fun last(): Optional<Receipt> {
        return Optional.ofNullable(history.lastOrNull())
    }

    override fun add(receipt: Receipt) {
        history.add(receipt)
    }

    override fun contains(receipt: Receipt): Boolean {
        return history.contains(receipt)
    }

    override fun containsInOrder(vararg records: Receipt): Boolean {
        val expected = listOf(*records)

        return (history.filter { receipt -> expected.contains(receipt) } == expected)
    }

    override fun getInitialBalance(): Balance = if (first().isPresent) first().get().amount else zeroBalance()

    override fun getCurrentBalance(): Balance = sum(total())

    override fun getPreviousBalance(): Balance = sum(total() - 1)

    override fun sum(numberOfTransactions: Int): Balance {
        return history.take(max(numberOfTransactions, 0))
            .map { receipt -> receipt.amount }
            .fold(zeroBalance()) { balance, receiptAmount -> balance.plus(receiptAmount) }
    }

    private fun zeroBalance() = Balance.zero(currency)
}
