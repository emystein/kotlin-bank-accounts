package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts

class InMemoryBankAccounts: BankAccounts {
    private val accounts: MutableList<SavingsAccount> = mutableListOf()

    override fun create(customer: Customer, currency: String): SavingsAccount {
        val created = SavingsAccount(customer, currency)
        accounts.add(created)
        return created
    }

    override fun contains(account: SavingsAccount): Boolean {
        return accounts.contains(account)
    }
}
