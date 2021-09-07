package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount

class SavingsAccountMapper(
    private val customerRepository: CustomerRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = CustomerMapper().toDomain(bankAccount.owner)
        return SavingsAccount(owner, bankAccount.currency)
    }

    fun toJpa(bankAccount: BankAccount): ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount {
        val customerName = bankAccount.owner.name
        val maybeJpaOwner = customerRepository.findByName(customerName)
        val jpaOwner: Customer = if (maybeJpaOwner.isPresent) {
            maybeJpaOwner.get()
        } else {
            customerRepository.save(Customer(0, customerName))
        }

        val currency = bankAccount.currency
        val maybeJpaBankAccount = bankAccountRepository.findByOwnerAndCurrency(jpaOwner, currency)
        return if (maybeJpaBankAccount.isPresent) {
            maybeJpaBankAccount.get()
        } else {
            bankAccountRepository.save(BankAccount(0L, jpaOwner, currency))
        }
    }
}
