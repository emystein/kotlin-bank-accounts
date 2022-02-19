package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryStatement
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.BankAccountRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class BankAccountRegistry(
    @Autowired
    private val receiptMapper: ReceiptMapper,
    @Autowired
    private val receiptRepository: ReceiptRepository
) : BankAccountRegistry {
    override fun createSavingsAccount(accountOwner: Customer, currency: Currency): SavingsAccount {
        val accountId = generateId()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        return SavingsAccount(accountId, accountOwner, currency, Statement(currency, receipts))
    }

    override fun createCheckingAccountFor(accountOwner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount {
        val accountId = generateId()
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        val account = SavingsAccount(generateId(), accountOwner, currency, Statement(currency, receipts))
        account.withdrawalLimit = withdrawalLimit
        return account
    }

    fun createSavingsAccount(accountId: AccountId, owner: Customer, currency: Currency): SavingsAccount {
        val receipts = AccountReceipts(accountId, receiptMapper, receiptRepository)
        return SavingsAccount(accountId, owner, currency, Statement(currency, receipts))
    }

    private fun generateId(): AccountId {
        return AccountId(UUID.randomUUID().toString())
    }
}