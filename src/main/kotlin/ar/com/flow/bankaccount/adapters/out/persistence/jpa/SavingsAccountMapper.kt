package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SavingsAccountMapper(
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val receiptRepository: ReceiptRepository
) {
    private val customerMapper = CustomerMapper(customerRepository)
    private val receiptMapper = ReceiptMapper(customerMapper)

    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(bankAccount.owner)
        val statement = Statement(owner, bankAccount.currency,
            customerRepository,
            receiptMapper, receiptRepository)
        return SavingsAccount(owner, bankAccount.currency, statement)
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
