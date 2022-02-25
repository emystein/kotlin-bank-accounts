package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SavingsAccountMapper(
    @Autowired private val customerMapper: CustomerMapper,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val receiptMapper: ReceiptMapper,
    @Autowired private val receiptRepository: ReceiptRepository
) {
    fun toDomain(account: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): SavingsAccount {
        val owner = customerMapper.toDomain(account.owner)

        val accountId = AccountId(account.accountId)

        return createSavingsAccount(accountId, owner, Currency.valueOf(account.currency))
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

    private fun createSavingsAccount(accountId: AccountId, owner: Customer, currency: Currency): SavingsAccount {
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        return SavingsAccount(accountId, owner, currency, Statement(currency, receipts))
    }
}
