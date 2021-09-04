package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.SavingsAccount

interface BankAccounts {
    fun create(savedCustomer: Customer, currency: String): SavingsAccount

    fun accountOwnedBy(customer: Customer, currency: String): SavingsAccount?

    fun contains(createdAccount: SavingsAccount): Boolean
}
