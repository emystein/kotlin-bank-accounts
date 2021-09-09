package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReceiptMapper(@Autowired private val customerMapper: CustomerMapper) {
    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt): Receipt {
        return Receipt(
            customerMapper.toDomain(receipt.customer),
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            Balance.create(receipt.currency, receipt.amount),
            Balance.create(receipt.currency, receipt.resultAmount),
        )
    }

    fun toJpa(receipt: Receipt): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt {
        return Receipt(
            0,
            customerMapper.toJpa(receipt.customer),
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            receipt.amount.currency,
            receipt.amount.amount,
            receipt.resultBalance.amount
        )
    }
}
