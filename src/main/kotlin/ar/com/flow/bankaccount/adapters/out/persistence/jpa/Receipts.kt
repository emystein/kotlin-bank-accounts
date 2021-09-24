package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Receipts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Receipts(
    @Autowired
    private val receiptMapper: ReceiptMapper,
    @Autowired
    private val receiptRepository: ReceiptRepository
) : Receipts {
    override fun all(accountId: AccountId): List<Receipt> {
        return receiptRepository.findAllByAccountId(accountId.value)
            .map { receiptMapper.toDomain(it) }
    }

    override fun add(receipt: Receipt) {
        receiptRepository.save(receiptMapper.toJpa(receipt))
    }
}
