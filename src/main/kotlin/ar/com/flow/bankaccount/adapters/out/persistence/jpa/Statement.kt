package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Statement

class Statement(
    private val accountId: AccountId,
    override val currency: Currency,
    private val receipts: AccountReceipts
) : Statement {
    override fun all(): Collection<Receipt> {
        return receipts.all()
    }

    override fun add(receipt: Receipt) {
        // TODO: add precondition of receipt account owner and currency must match this instance accountOwner and currency
        receipts.add(receipt)
    }
}
