package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.Receipts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SavingsAccountMapper(
    @Autowired private val customerMapper: CustomerMapper,
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val receipts: Receipts,
) {
    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(bankAccount.owner)
        return SavingsAccount(owner, bankAccount.currency, Statement(owner, bankAccount.currency, receipts))
    }

    fun toJpa(bankAccount: BankAccount): ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount {
        val customerName = bankAccount.owner.name
        val maybeOwner = customerRepository.findByName(customerName)
        val owner = if (maybeOwner.isPresent) {
            maybeOwner.get()
        } else {
            customerRepository.save(Customer(0, customerName))
        }

        val currency = bankAccount.currency
        val maybeBankAccount = bankAccountRepository.findByOwnerAndCurrency(owner, currency)
        return if (maybeBankAccount.isPresent) {
            maybeBankAccount.get()
        } else {
            bankAccountRepository.save(BankAccount(0L, owner, currency))
        }
    }
}
