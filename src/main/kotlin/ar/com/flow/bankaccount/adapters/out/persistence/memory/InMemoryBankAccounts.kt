package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.BankAccounts

class InMemoryBankAccounts : BankAccounts {
    private val accounts: MutableMap<Customer, MutableSet<BankAccount>> = mutableMapOf()

    override fun createSavingsAccount(accountOwner: Customer, currency: Currency): BankAccount {
        val created = SavingsAccount(generateId(), accountOwner, currency, InMemoryStatement(currency))

        if (accounts.containsKey(accountOwner)) {
            accounts[accountOwner]!!.add(created)
        } else {
            accounts[accountOwner] = mutableSetOf(created)
        }

        return created
    }

    override fun createCheckingAccount(owner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount {
        val account = SavingsAccount(generateId(), owner, currency, InMemoryStatement(currency))
        account.withdrawalLimit = withdrawalLimit
        return account
    }

    override fun save(account: BankAccount): BankAccount {
        accounts[account.owner]!!.add(account)

        return account
    }

    override fun ownedBy(accountOwner: Customer): List<BankAccount> {
        return if (accounts.containsKey(accountOwner)) {
            accounts[accountOwner]!!.toList()
        } else {
            listOf()
        }
    }

    override fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount> {
        return if (accounts.containsKey(accountOwner)) {
            accounts[accountOwner]!!.filter { account -> account.currency == currency }
        } else {
            listOf()
        }
    }

    override fun contains(account: BankAccount): Boolean {
        return accounts.containsKey(account.owner) &&
                accounts[account.owner]!!.any { a -> a.currency == account.currency }
    }
}
