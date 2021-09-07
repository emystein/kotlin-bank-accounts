package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount

class SavingsAccountMapper(
    private val customerRepository: CustomerRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    private val customerMapper = CustomerMapper()

    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(bankAccount.owner)
        return SavingsAccount(owner, bankAccount.currency)
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
