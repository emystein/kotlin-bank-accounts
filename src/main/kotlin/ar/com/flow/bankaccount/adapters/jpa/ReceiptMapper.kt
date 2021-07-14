package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*

class ReceiptMapper {
    fun toDomain(jpaReceipt: JpaReceipt?): Optional<Receipt> {
       return toDomain(Optional.ofNullable(jpaReceipt))
    }

    fun toDomain(maybeJpaReceipt: Optional<JpaReceipt>): Optional<Receipt> {
        return maybeJpaReceipt.map { toDomain(it)}
    }

    fun toDomain(jpaReceipt: JpaReceipt): Receipt {
        return Receipt(
            BankAccountMapper().toDomain(jpaReceipt.bankAccount),
            jpaReceipt.dateTime,
            jpaReceipt.movement,
            jpaReceipt.action,
            Balance.create(jpaReceipt.currency, jpaReceipt.amount),
            Balance.create(jpaReceipt.currency, jpaReceipt.resultAmount),
        )
    }

    fun toJpa(receipt: Receipt): JpaReceipt {
        val jpaBankAccount = BankAccountMapper().toJpa(receipt.destinationAccount)

        return JpaReceipt(
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
