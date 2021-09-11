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
    @Autowired private val accountRegistry: BankAccountRegistry,
) {
    fun toDomain(account: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(account.owner)

        return accountRegistry.createSavingsAccount(account.accountId, owner, Currency.valueOf(account.currency))
    }

    fun toJpa(account: BankAccount): ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount {
        val jpaAccount = bankAccountRepository.findByAccountId(account.id.value)

        return if (jpaAccount.isPresent) {
            jpaAccount.get()
        } else {
            val owner = customerMapper.toJpa(account.owner)
            bankAccountRepository.save(BankAccount(0L, account.id.value, owner, account.currency.code))
        }
    }
}
