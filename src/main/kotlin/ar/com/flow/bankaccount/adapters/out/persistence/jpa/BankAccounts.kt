package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.AccountIdGenerator
import ar.com.flow.bankaccount.ports.out.BankAccountFilters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

@Component
@Transactional
class BankAccounts(
    @Autowired private val customerRepository: CustomerRepository,
    @Autowired private val accountMapper: SavingsAccountMapper,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val receiptMapper: ReceiptMapper,
    @Autowired private val receiptRepository: ReceiptRepository,
    @Autowired private val accountIdGenerator: AccountIdGenerator,
) : ar.com.flow.bankaccount.ports.out.BankAccounts {
    override fun createSavingsAccount(owner: Customer, currency: Currency): BankAccount {
        val accountId = accountIdGenerator.generate()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        val account = SavingsAccount(accountId, owner, currency, Statement(currency, receipts))
        val persistenceAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(persistenceAccount)
        return account
    }

    override fun createCheckingAccount(owner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount {
        val accountId = accountIdGenerator.generate()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        val account = SavingsAccount(accountId, owner, currency, Statement(currency, receipts))
        account.withdrawalLimit = withdrawalLimit
        val persistenceAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(persistenceAccount)
        return account
    }

    override fun getById(accountId: AccountId): Optional<BankAccount> {
        val persistenceAccount = bankAccountRepository.findByAccountId(accountId.value)
        return persistenceAccount.map { accountMapper.toDomain(it) }
    }

    override fun save(account: BankAccount): BankAccount {
        val jpaAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(jpaAccount)
        return accountMapper.toDomain(jpaAccount)
    }

    override fun ownedBy(accountOwner: Customer): List<BankAccount> {
        return jpaAccountsOwnedBy(accountOwner).map { accountMapper.toDomain(it) }
    }

    override fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount> {
        return ownedBy(accountOwner).filter { it.currency == currency }
    }

    override fun contains(account: BankAccount): Boolean {
        return bankAccountRepository.findByAccountId(account.id.value).isPresent
    }

    override fun query(filters: BankAccountFilters): List<BankAccount> {
        return filters.applyTo(all())
    }

    override fun all(): List<BankAccount> {
        return bankAccountRepository.findAll().map{ account -> accountMapper.toDomain(account) }
    }

    private fun jpaAccountsOwnedBy(accountOwner: Customer): List<ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findAllByOwner(maybeJpaAccountOwner.get())
        } else {
            listOf()
        }
    }
}