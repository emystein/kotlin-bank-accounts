package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
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

    override fun accountOwnedBy(accountOwner: Customer, currency: String): SavingsAccount {
        val maybeAccount = maybeJpaAccountOwnedBy(accountOwner, currency)

        if (!maybeAccount.isPresent) {
            throw AccountNotFound("Account not found for Customer: ${accountOwner.name} and currency: $currency")
        }

        return accountMapper.toDomain(maybeAccount.get())
    }

    override fun contains(account: SavingsAccount): Boolean {
        return maybeJpaAccountOwnedBy(account.owner, account.currency).isPresent
    }

    private fun maybeJpaAccountOwnedBy(accountOwner: Customer, currency: String): Optional<BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findByOwnerAndCurrency(maybeJpaAccountOwner.get(), currency)
        } else {
            Optional.empty()
        }
    }
}