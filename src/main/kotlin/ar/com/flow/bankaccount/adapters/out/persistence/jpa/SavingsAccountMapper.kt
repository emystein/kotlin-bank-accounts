package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SavingsAccountMapper(
    @Autowired private val customerMapper: CustomerMapper,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val savingsAccountFactory: SavingsAccountFactory,
) {
    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(bankAccount.owner)
        return savingsAccountFactory.createSavingsAccount(owner, Currency.valueOf(bankAccount.currency))
    }

    fun toJpa(bankAccount: BankAccount): ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount {
        val owner = customerMapper.toJpa(bankAccount.owner)

        val currency = bankAccount.currency.code
        val maybeBankAccount = bankAccountRepository.findByOwnerAndCurrency(owner, currency)
        return if (maybeBankAccount.isPresent) {
            maybeBankAccount.get()
        } else {
            bankAccountRepository.save(BankAccount(0L, owner, currency))
        }
    }
}
