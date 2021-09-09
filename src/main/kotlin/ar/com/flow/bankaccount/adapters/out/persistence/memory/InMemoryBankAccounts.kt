package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts
import java.util.*

class InMemoryBankAccounts: BankAccounts {
    private val accounts: MutableMap<Customer, MutableMap<Currency, BankAccount>> = mutableMapOf()

    override fun create(customer: Customer, currency: Currency): BankAccount {
        val created = SavingsAccount(customer, currency, InMemoryStatement(currency))

        if (accounts.containsKey(customer)) {
            accounts[customer]!![currency] = created
        } else {
            accounts[customer] = mutableMapOf(currency to created)
        }

        return created
    }

    override fun save(account: BankAccount): BankAccount {
        accounts[account.owner] = mutableMapOf(account.currency to account)

        return account
    }

    override fun accountOwnedBy(customer: Customer, currency: Currency): Optional<BankAccount> {
        return if (accounts.containsKey(customer) && accounts[customer]!!.containsKey(currency)) {
            Optional.ofNullable(accounts[customer]!![currency])
        } else {
            Optional.empty()
        }
    }

    override fun contains(account: BankAccount): Boolean {
        return (accounts.containsKey(account.owner) && accounts[account.owner]!!.containsKey(account.currency))
    }
}
