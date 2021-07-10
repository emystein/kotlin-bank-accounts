package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.money.Money
import java.util.*

class InMemoryStatement : Statement {
    private val history: MutableList<Receipt> = ArrayList()

    override fun total(): Int {
        return history.size
    }

    override fun first(): Optional<Receipt> {
        return if (history.isNotEmpty()) {
            Optional.of(history[0])
        } else {
            Optional.empty()
        }
    }

    override fun last(): Optional<Receipt> {
        return if (history.isNotEmpty()) {
            Optional.of(history[history.size - 1])
        } else {
            Optional.empty()
        }
    }

    override fun add(receipt: Receipt) {
        history.add(receipt)
    }

    override fun contains(receipt: Receipt): Boolean {
        return history.contains(receipt)
    }

    override fun containsInOrder(vararg records: Receipt): Boolean {
        val indexedMovements: List<Receipt> = Arrays.asList(*records)

        return (history.filter { o: Receipt -> indexedMovements.contains(o) }
                == indexedMovements)
    }

    override val currentBalance: Optional<Balance>
        get() = sum(total())
    override val previousBalance: Optional<Balance>
        get() = sum(total() - 1)
    override val initialBalance: Optional<Balance>
        get() = first().map(Receipt::amount)

    private fun sum(numberOfTransactions: Int): Optional<Balance> {
        if (history.isNotEmpty()) {
            val currency: String = history.first().amount.currency

            val amount: Int = entries(numberOfTransactions)
                .map { it.amount }
                .sumOf { it.amount }

            return Optional.of(Balance.create(currency, amount))
        } else {
            return Optional.empty()
        }
    }

    private fun entries(numberOfEntries: Int): List<Receipt> {
        return if (history.isNotEmpty()) {
            history.take(numberOfEntries)
        } else {
            listOf()
        }
    }
}