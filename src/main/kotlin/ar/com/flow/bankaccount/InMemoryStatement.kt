package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.transaction.receipt.Receipt
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

    override val currentBalance: Balance
        get() = sum(total())
    override val previousBalance: Balance
        get() = sum(total() - 1)
    override val initialBalance: Balance
        get() = if (first().isPresent) first().get().amount else zeroBalance()

    override fun sum(numberOfTransactions: Int): Balance {
        return history.take(max(0, numberOfTransactions))
            .map { receipt -> receipt.amount }
            .fold(zeroBalance()) { balance, receiptAmount -> balance.plus(receiptAmount) }
    }

    private fun zeroBalance() = Balance.zero(currency)
}
