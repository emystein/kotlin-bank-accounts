package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.Receipts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountRegistry(@Autowired private val receipts: Receipts) {
    fun createSavingsAccount(accountId: String, owner: Customer, currency: Currency): SavingsAccount {
        return SavingsAccount(AccountId(accountId), owner, currency, Statement(owner, currency, receipts))
    }

    fun createSavingsAccount(owner: Customer, currency: Currency): SavingsAccount {
        return SavingsAccount(generateId(), owner, currency, Statement(owner, currency, receipts))
    }

    private fun generateId(): AccountId {
        return AccountId(UUID.randomUUID().toString())
    }
}