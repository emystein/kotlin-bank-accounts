package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class JpaSavingsAccounts(
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val bankAccountRepository: BankAccountRepository
) : SavingsAccounts {
    private val accountMapper: SavingsAccountMapper = SavingsAccountMapper(customerRepository, bankAccountRepository)

    override fun create(owner: Customer, currency: String): SavingsAccount {
        val jpaAccount = accountMapper.toJpa(SavingsAccount(owner, currency))
        val createdJpaAccount = bankAccountRepository.save(jpaAccount)
        bankAccountRepository.flush()
        return accountMapper.toDomain(createdJpaAccount)
    }

    override fun save(account: SavingsAccount) {
        val jpaAccount = accountMapper.toJpa(account)
        val createdJpaAccount = bankAccountRepository.save(jpaAccount)
        accountMapper.toDomain(createdJpaAccount)
    }

    override fun accountOwnedBy(owner: Customer, currency: String): SavingsAccount {
        val maybeAccountOwner = customerRepository.findByName(owner.name)

        if (maybeAccountOwner.isPresent) {
            val maybeJpaAccount = bankAccountRepository.findByOwnerAndCurrency(maybeAccountOwner.get(), currency)

            if (maybeJpaAccount.isPresent) {
                return accountMapper.toDomain(maybeJpaAccount.get())
            } else {
                throw AccountNotFound("Account not found for Customer: ${owner.name} and currency: $currency")
            }
        } else {
            throw AccountNotFound("Account not found for Customer: ${owner.name} and currency: $currency")
        }
    }

    override fun contains(account: SavingsAccount): Boolean {
        val maybeAccountOwner = customerRepository.findByName(account.owner.name)

        if (maybeAccountOwner.isPresent) {
            val maybeJpaAccount = bankAccountRepository.findByOwnerAndCurrency(maybeAccountOwner.get(), account.currency)
            return maybeJpaAccount.isPresent
        } else {
            return false
        }
    }
}