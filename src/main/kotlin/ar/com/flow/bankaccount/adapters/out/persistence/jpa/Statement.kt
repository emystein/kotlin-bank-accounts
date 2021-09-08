package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt

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

    override fun add(receipt: Receipt) {
        val jpaReceipt = receiptMapper.toJpa(receipt)
        receiptRepository.save(jpaReceipt)
    }
}
