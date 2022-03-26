package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.BankAccount

class BankAccountFilter(private val filter: (BankAccount) -> Boolean) {
    fun applyTo(accountsToFilter: List<BankAccount>): List<BankAccount> {
        return accountsToFilter.filter(filter)
    }
}
