package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Receipts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class JpaReceipts(
    @Autowired
    private val customerMapper: CustomerMapper,
    @Autowired
    private val receiptMapper: ReceiptMapper,
    @Autowired
    private val receiptRepository: ReceiptRepository
) : Receipts {
    override fun all(accountOwner: ar.com.flow.Customer, currency: String): List<Receipt> {
        val customer = customerMapper.toJpa(accountOwner)
        return receiptRepository.findAllByCustomerAndCurrency(customer, currency)
            .map { receiptMapper.toDomain(it) }
    }

    override fun add(receipt: Receipt) {
        receiptRepository.save(receiptMapper.toJpa(receipt))
    }
}
