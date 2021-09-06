package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.domain.SavingsAccount
import kotlin.jvm.Throws

interface SavingsAccounts {
    fun create(owner: Customer, currency: String): SavingsAccount

    fun save(account: SavingsAccount)

    @Throws(AccountNotFound::class)
    fun accountOwnedBy(customer: Customer, currency: String): SavingsAccount

    fun contains(account: SavingsAccount): Boolean
}
