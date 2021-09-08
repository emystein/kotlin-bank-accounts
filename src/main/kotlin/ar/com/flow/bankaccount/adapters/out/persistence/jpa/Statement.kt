package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*
import kotlin.math.max

class Statement(
    private val accountOwner: Customer,
    override val currency: String,
    private val customerRepository: CustomerRepository,
    private val receiptMapper: ReceiptMapper,
    private val receiptRepository: ReceiptRepository
) : ar.com.flow.bankaccount.ports.out.Statement {

    override fun all(): Collection<Receipt> {
        val customer = customerRepository.findByName(accountOwner.name).get()
        return receiptRepository.findAllByCustomerAndCurrency(customer, currency)
            .map { receiptMapper.toDomain(it) }
            .sortedBy { it.dateTime }
    }

    override fun count(): Int {
        return all().size
    }

    override fun first(): Optional<Receipt> {
        return Optional.ofNullable(all().firstOrNull())
    }

    override fun last(): Optional<Receipt> {
        return Optional.ofNullable(all().lastOrNull())
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Statement

        if (accountOwner != other.accountOwner) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accountOwner.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }
}
