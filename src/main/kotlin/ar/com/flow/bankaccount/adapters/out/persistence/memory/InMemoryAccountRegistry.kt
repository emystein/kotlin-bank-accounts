package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.BankAccountRegistry
import java.util.*

class InMemoryAccountRegistry : BankAccountRegistry {
    override fun createSavingsAccount(accountOwner: Customer, currency: Currency): BankAccount {
        return SavingsAccount(generateId(), accountOwner, currency, InMemoryStatement(currency))
    }

    override fun createCheckingAccountFor(accountOwner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount {
        val account = SavingsAccount(generateId(), accountOwner, currency, InMemoryStatement(currency))
        account.withdrawalLimit = withdrawalLimit
        return account
    }

    private fun generateId(): AccountId {
        return AccountId(UUID.randomUUID().toString())
    }
}