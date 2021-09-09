package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Receipts
import ar.com.flow.bankaccount.ports.out.Statement

class Statement(
    private val accountOwner: Customer,
    override val currency: String,
    private val receipts: Receipts,
) : Statement {

    override fun all(): Collection<Receipt> {
        return receipts.all(accountOwner, currency)
    }

    override fun add(receipt: Receipt) {
        receipts.add(receipt)
    }
}
