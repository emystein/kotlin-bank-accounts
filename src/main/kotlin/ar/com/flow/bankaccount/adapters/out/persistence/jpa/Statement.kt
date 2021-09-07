package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*
import kotlin.math.max

class Statement(
    override val currency: String,
    private val receiptMapper: ReceiptMapper,
    private val receiptRepository: ReceiptRepository
) : ar.com.flow.bankaccount.ports.out.Statement {

    override fun all(): Collection<Receipt> {
        return receiptRepository.findAll()
            .map { receiptMapper.toDomain(it) }
            .sortedBy { it.dateTime }
    }

    override fun count(): Int {
        return all().size
    }

    override fun first(): Optional<Receipt> {
        val jpaReceipt = receiptRepository.findAll().firstOrNull()
        return receiptMapper.toDomain(jpaReceipt)
    }

    override fun last(): Optional<Receipt> {
        val jpaReceipt = receiptRepository.findAll().lastOrNull()
        return receiptMapper.toDomain(jpaReceipt)
    }

    override fun add(receipt: Receipt) {
        val jpaReceipt = receiptMapper.toJpa(receipt)
        receiptRepository.save(jpaReceipt)
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
        receiptRepository.deleteAll()
    }
}
