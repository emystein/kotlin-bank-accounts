package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Statement

class InMemoryStatement(override val currency: String) : Statement {
    private val history: MutableList<Receipt> = ArrayList()

    override fun all(): Collection<Receipt> {
        return history
    }

    override fun add(receipt: Receipt) {
        history.add(receipt)
    }
}
