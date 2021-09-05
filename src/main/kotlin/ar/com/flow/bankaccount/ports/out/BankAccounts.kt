package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.domain.SavingsAccount
import kotlin.jvm.Throws

interface BankAccounts {
    fun create(savedCustomer: Customer, currency: String): SavingsAccount

    @Throws(AccountNotFound::class)
    fun accountOwnedBy(customer: Customer, currency: String): SavingsAccount

    fun contains(createdAccount: SavingsAccount): Boolean
    fun save(savingsAccount: SavingsAccount)
}
