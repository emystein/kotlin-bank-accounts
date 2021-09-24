package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import org.springframework.stereotype.Component

@Component
class ReceiptMapper {
    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt): Receipt {
        val currency = Currency.valueOf(receipt.currency)

        return Receipt(
            AccountId(receipt.accountId),
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            Balance.create(currency, receipt.amount)
        )
    }

    fun toJpa(receipt: Receipt): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt {
        return Receipt(
            0,
            receipt.accountId.value,
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            receipt.amount.currency.code,
            receipt.amount.amount,
        )
    }
}
