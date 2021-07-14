package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.Statement
import java.util.*
import kotlin.math.max

class JpaStatement(override val currency: String, val jpaReceiptRepository: ReceiptRepository) : Statement {
    override fun all(): Collection<Receipt> {
        return jpaReceiptRepository.findAll()
            .map { ReceiptMapper().toDomain(it) }
            .sortedBy { it.dateTime }
    }

    override fun count(): Int {
        return all().size
    }

    override fun first(): Optional<Receipt> {
        val jpaReceipt = jpaReceiptRepository.findAll().firstOrNull()
        return ReceiptMapper().toDomain(jpaReceipt)
    }

    override fun last(): Optional<Receipt> {
        val jpaReceipt = jpaReceiptRepository.findAll().lastOrNull()
        return ReceiptMapper().toDomain(jpaReceipt)
    }

    override fun add(receipt: Receipt) {
        val jpaReceipt = ReceiptMapper().toJpa(receipt)
        jpaReceiptRepository.save(jpaReceipt)
    }

    override fun contains(receipt: Receipt): Boolean {
        return containsInOrder(receipt)
    }

    override fun containsInOrder(vararg records: Receipt): Boolean {
        val expected = listOf(*records)

        val stored = all().filter { receipt -> expected.contains(receipt) }

        return (stored == expected)
    }

    override fun getInitialBalance(): Balance = if (first().isPresent) first().get().amount else zeroBalance()

    override fun getCurrentBalance(): Balance = sum(count())

    override fun getPreviousBalance(): Balance = sum(count() - 1)

    override fun sum(numberOfTransactions: Int): Balance {
        return all().take(max(numberOfTransactions, 0))
            .map { receipt -> receipt.amount }
            .fold(zeroBalance()) { balance, receiptAmount -> balance.plus(receiptAmount) }
    }

    override fun clear() {
        jpaReceiptRepository.deleteAll()
    }
}
