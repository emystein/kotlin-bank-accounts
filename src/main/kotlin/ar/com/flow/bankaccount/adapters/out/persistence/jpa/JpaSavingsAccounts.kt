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
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val accountMapper: SavingsAccountMapper,
    @Autowired private val receiptRepository: ReceiptRepository,
) : SavingsAccounts {

    override fun create(owner: Customer, currency: String): SavingsAccount {
        val receiptMapper = ReceiptMapper(accountMapper)
        val statement = Statement(owner, currency, customerRepository, bankAccountRepository, receiptMapper, receiptRepository)
        val savingsAccount = SavingsAccount(owner, currency, statement)
        val jpaAccount = accountMapper.toJpa(savingsAccount)
        val createdJpaAccount = bankAccountRepository.save(jpaAccount)
        bankAccountRepository.flush()
        return accountMapper.toDomain(createdJpaAccount)
    }

    override fun save(account: SavingsAccount) {
        val jpaAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(jpaAccount)
        bankAccountRepository.flush()
    }

    override fun accountOwnedBy(accountOwner: Customer, currency: String): SavingsAccount {
        val maybeJpaAccount = maybeJpaAccountOwnedBy(accountOwner, currency)

        if (!maybeJpaAccount.isPresent) {
            throw AccountNotFound("Account not found for Customer: ${accountOwner.name} and currency: $currency")
        }

        return accountMapper.toDomain(maybeJpaAccount.get())
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