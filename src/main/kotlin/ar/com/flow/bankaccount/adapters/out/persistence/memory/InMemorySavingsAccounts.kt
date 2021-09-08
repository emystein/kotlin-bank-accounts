package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.adapters.out.persistence.jpa.CustomerMapper
import ar.com.flow.bankaccount.adapters.out.persistence.jpa.Statement
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.SavingsAccounts

class InMemorySavingsAccounts: SavingsAccounts {
    private val accounts: MutableMap<Customer, MutableMap<String, SavingsAccount>> = mutableMapOf()

    override fun create(customer: Customer, currency: String): SavingsAccount {
        val created = SavingsAccount(customer, currency, InMemoryStatement(currency))

        if (accounts.containsKey(customer)) {
            accounts[customer]!![currency] = created
        } else {
            accounts[customer] = mutableMapOf(currency to created)
        }

        return created
    }

    override fun save(account: SavingsAccount) {
        accounts[account.owner] = mutableMapOf(account.currency to account)
    }

    override fun accountOwnedBy(customer: Customer, currency: String): SavingsAccount {
        if (accounts.containsKey(customer) && accounts[customer]!!.containsKey(currency)) {
            return accounts[customer]!![currency]!!
        } else {
            throw AccountNotFound("Account not found for Customer: ${customer.name} and Currency: $currency")
        }
    }

    override fun contains(account: SavingsAccount): Boolean {
        return (accounts.containsKey(account.owner) && accounts[account.owner]!!.containsKey(account.currency))
    }
}
