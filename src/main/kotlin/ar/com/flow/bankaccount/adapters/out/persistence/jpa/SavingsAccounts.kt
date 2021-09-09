package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

@Component
@Transactional
class SavingsAccounts(
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val accountMapper: SavingsAccountMapper,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val savingsAccountFactory: SavingsAccountFactory
) : BankAccounts {

    override fun create(owner: Customer, currency: Currency): BankAccount {
        val newAccount = savingsAccountFactory.createSavingsAccount(owner, currency)
        return save(newAccount)
    }

    override fun save(account: BankAccount): BankAccount {
        val jpaAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(jpaAccount)
        return accountMapper.toDomain(jpaAccount)
    }

    override fun accountOwnedBy(accountOwner: Customer, currency: Currency): Optional<BankAccount> {
        val maybeJpaAccount = maybeJpaAccountOwnedBy(accountOwner, currency)
        return maybeJpaAccount.map { jpaAccount -> accountMapper.toDomain(jpaAccount) }
    }

    override fun contains(account: BankAccount): Boolean {
        return maybeJpaAccountOwnedBy(account.owner, account.currency).isPresent
    }

    private fun maybeJpaAccountOwnedBy(accountOwner: Customer, currency: Currency): Optional<ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findByOwnerAndCurrency(maybeJpaAccountOwner.get(), currency.code)
        } else {
            Optional.empty()
        }
    }
}