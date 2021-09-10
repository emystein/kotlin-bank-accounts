package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class SavingsAccounts(
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val accountMapper: SavingsAccountMapper,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val accountRegistry: AccountRegistry
) : BankAccounts {

    override fun create(accountOwner: Customer, currency: Currency): BankAccount {
        val newAccount = accountRegistry.createSavingsAccount(accountOwner, currency)
        return save(newAccount)
    }

    override fun save(account: BankAccount): BankAccount {
        val jpaAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(jpaAccount)
        return accountMapper.toDomain(jpaAccount)
    }

    override fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount> {
        return jpaAccountsOwnedBy(accountOwner, currency).map { jpaAccount -> accountMapper.toDomain(jpaAccount) }
    }

    override fun contains(account: BankAccount): Boolean {
        return jpaAccountsOwnedBy(account.owner, account.currency).isNotEmpty()
    }

    private fun jpaAccountsOwnedBy(accountOwner: Customer, currency: Currency): List<ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findAllByOwnerAndCurrency(maybeJpaAccountOwner.get(), currency.code)
        } else {
            listOf()
        }
    }
}