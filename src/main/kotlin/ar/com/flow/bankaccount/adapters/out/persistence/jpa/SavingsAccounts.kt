package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Receipts
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
    @Autowired private val receipts: Receipts,
) : BankAccounts {

    override fun create(owner: Customer, currency: String): BankAccount {
        val statement = Statement(owner, currency, receipts)
        val savingsAccount = SavingsAccount(owner, currency, statement)
        val jpaAccount = accountMapper.toJpa(savingsAccount)
        val createdJpaAccount = bankAccountRepository.save(jpaAccount)
        bankAccountRepository.flush()
        return accountMapper.toDomain(createdJpaAccount)
    }

    override fun save(account: BankAccount) {
        val jpaAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(jpaAccount)
        bankAccountRepository.flush()
    }

    override fun accountOwnedBy(accountOwner: Customer, currency: String): Optional<BankAccount> {
        val maybeJpaAccount = maybeJpaAccountOwnedBy(accountOwner, currency)
        return maybeJpaAccount.map { jpaAccount -> accountMapper.toDomain(jpaAccount) }
    }

    override fun contains(account: BankAccount): Boolean {
        return maybeJpaAccountOwnedBy(account.owner, account.currency).isPresent
    }

    private fun maybeJpaAccountOwnedBy(accountOwner: Customer, currency: String): Optional<ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findByOwnerAndCurrency(maybeJpaAccountOwner.get(), currency)
        } else {
            Optional.empty()
        }
    }
}