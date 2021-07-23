package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*

class ReceiptMapper {
    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt?): Optional<Receipt> {
       return toDomain(Optional.ofNullable(receipt))
    }

    fun toDomain(maybeReceipt: Optional<ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt>): Optional<Receipt> {
        return maybeReceipt.map { toDomain(it)}
    }

    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt): Receipt {
        return Receipt(
            BankAccountMapper().toDomain(receipt.bankAccount),
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            Balance.create(receipt.currency, receipt.amount),
            Balance.create(receipt.currency, receipt.resultAmount),
        )
    }

    fun toJpa(receipt: Receipt): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt {
        val jpaBankAccount = BankAccountMapper().toJpa(receipt.destinationAccount)

        return Receipt(
            0,
            jpaBankAccount,
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            receipt.amount.currency,
            receipt.amount.amount,
            receipt.resultBalance.amount
        )
    }
}
