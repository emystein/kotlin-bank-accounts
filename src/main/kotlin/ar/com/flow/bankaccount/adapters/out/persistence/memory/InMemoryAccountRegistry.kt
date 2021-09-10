package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.money.Money

object InMemoryAccountRegistry {
    fun createSavingsAccountFor(accountOwner: Customer, currency: Currency): BankAccount {
        return SavingsAccount(accountOwner, currency, InMemoryStatement(currency))
    }

    fun createSavingsAccountFor(accountOwner: Customer, initialBalance: Money): BankAccount {
        val account = SavingsAccount(accountOwner, initialBalance.currency, InMemoryStatement(initialBalance.currency))
        account.deposit(initialBalance)
        return account
    }

    fun createCheckingAccountFor(accountOwner: Customer, initialBalance: Money, withdrawalLimit: WithdrawalLimit): BankAccount {
        val account = SavingsAccount(accountOwner, Currency.USD, InMemoryStatement(Currency.USD))
        account.withdrawalLimit = withdrawalLimit
        account.deposit(initialBalance)
        return account
    }
}