package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency

class BankAccountFilters {
    var owner: Customer? = null
    var currency: Currency? = null

    var filters: MutableList<BankAccountFilter> = mutableListOf()

    fun owner(ownerToFilter: Customer): BankAccountFilters {
        this.owner = ownerToFilter
        filters.add(BankAccountFilter { account -> account.owner == ownerToFilter })
        return this
    }

    fun currency(currencyToFilter: Currency): BankAccountFilters {
        this.currency = currencyToFilter
        filters.add(BankAccountFilter { account -> account.currency == currencyToFilter})
        return this
    }

    fun add(filter: BankAccountFilter): BankAccountFilters {
        filters.add(filter)
        return this
    }

    fun applyTo(accountsToFilter: List<BankAccount>): List<BankAccount> {
        var filtered = accountsToFilter
        filters.forEach { filter ->
            filtered = filter.applyTo(filtered)
        }
        return filtered
    }
}
