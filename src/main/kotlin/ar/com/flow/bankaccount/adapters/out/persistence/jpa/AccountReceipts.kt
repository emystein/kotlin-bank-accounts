package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.DifferentAccountException
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.AccountReceipts

class AccountReceipts(
    override val accountId: AccountId,
    private val receiptMapper: ReceiptMapper,
    private val receiptRepository: ReceiptRepository
) : AccountReceipts {
    override fun all(): List<Receipt> {
        return receiptRepository.findAllByAccountId(accountId.value)
            .map { receiptMapper.toDomain(it) }
    }

    override fun add(receipt: Receipt) {
        if (receipt.accountId != accountId) {
            throw DifferentAccountException()
        }

        receiptRepository.save(receiptMapper.toJpa(receipt))
    }
}
