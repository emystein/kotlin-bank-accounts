package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.AccountIdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
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
    override fun createSavingsAccount(accountOwner: Customer, currency: Currency): BankAccount {
        val accountId = accountIdGenerator.generate()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        val account = SavingsAccount(accountId, accountOwner, currency, Statement(currency, receipts))
        val persistenceAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(persistenceAccount)
        return account
    }

    override fun createCheckingAccount(accountOwner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount {
        val accountId = accountIdGenerator.generate()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        val account = SavingsAccount(accountId, accountOwner, currency, Statement(currency, receipts))
        account.withdrawalLimit = withdrawalLimit
        val persistenceAccount = accountMapper.toJpa(account)
        bankAccountRepository.save(persistenceAccount)
        return account
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

    private fun jpaAccountsOwnedBy(accountOwner: Customer): List<ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount> {
        val maybeJpaAccountOwner = customerRepository.findByName(accountOwner.name)

        return if (maybeJpaAccountOwner.isPresent) {
            bankAccountRepository.findAllByOwner(maybeJpaAccountOwner.get())
        } else {
            listOf()
        }
    }
}