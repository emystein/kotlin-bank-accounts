package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import java.util.*

class ReceiptMapper(private val customerRepository: CustomerRepository, private val bankAccountRepository: BankAccountRepository) {
    private val savingsAccountMapper = SavingsAccountMapper(customerRepository, bankAccountRepository)

    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt?): Optional<Receipt> {
       return toDomain(Optional.ofNullable(receipt))
    }

    fun toDomain(maybeReceipt: Optional<ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt>): Optional<Receipt> {
        return maybeReceipt.map { toDomain(it)}
    }

    fun toDomain(receipt: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt): Receipt {
        val bankAccount = savingsAccountMapper.toDomain(receipt.bankAccount)

        return Receipt(
            bankAccount,
            receipt.dateTime,
            receipt.movement,
            receipt.action,
            Balance.create(receipt.currency, receipt.amount),
            Balance.create(receipt.currency, receipt.resultAmount),
        )
    }

    fun toJpa(receipt: Receipt): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Receipt {
        val customerName = receipt.destinationAccount.owner.name
        val maybeJpaOwner = customerRepository.findByName(customerName)
        val jpaOwner: Customer = if (maybeJpaOwner.isPresent) {
            maybeJpaOwner.get()
        } else {
            customerRepository.save(Customer(0, customerName))
        }

        val currency = receipt.destinationAccount.currency
        val maybeJpaBankAccount = bankAccountRepository.findByOwnerAndCurrency(jpaOwner, currency)
        val jpaBankAccount: BankAccount = if (maybeJpaBankAccount.isPresent) {
            maybeJpaBankAccount.get()
        } else {
            bankAccountRepository.save(BankAccount(0L, jpaOwner, currency))
        }

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
