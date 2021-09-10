package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency

interface BankAccounts {
    fun create(accountOwner: Customer, currency: Currency): BankAccount

    fun save(account: BankAccount): BankAccount

    fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount>

    fun contains(account: BankAccount): Boolean
}
