package ar.com.flow.bankaccount.adapters

import ar.com.flow.bankaccount.adapters.jpa.ReceiptRepository
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.Statement
import java.util.*

class JpaStatement(override val currency: String, val jpaReceiptRepository: ReceiptRepository) : Statement {
    override fun count(): Int {
        TODO("Not yet implemented")
    }

    override fun first(): Optional<Receipt> {
        val o = jpaReceiptRepository.findAll().firstOrNull()
        // TODO implement
//        return Optional.of(o.toModel())
        return Optional.empty()
    }

    override fun last(): Optional<Receipt> {
        TODO("Not yet implemented")
    }

    override fun add(receipt: Receipt) {
        TODO("Not yet implemented")
    }

    override fun contains(receipt: Receipt): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsInOrder(vararg records: Receipt): Boolean {
        TODO("Not yet implemented")
    }

    override fun getInitialBalance(): Balance {
        return Balance.zero(currency)
    }

    override fun getCurrentBalance(): Balance {
        return Balance.zero(currency)
    }

    override fun getPreviousBalance(): Balance {
        return Balance.zero(currency)
    }

    override fun sum(numberOfTransactions: Int): Balance {
        TODO("Not yet implemented")
    }

}
