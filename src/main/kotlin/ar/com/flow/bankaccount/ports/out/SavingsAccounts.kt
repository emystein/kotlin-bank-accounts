package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.SavingsAccount
import java.util.*

interface SavingsAccounts {
    fun create(owner: Customer, currency: String): SavingsAccount

    fun save(account: SavingsAccount)

    fun accountOwnedBy(accountOwner: Customer, currency: String): Optional<SavingsAccount>

    fun contains(account: SavingsAccount): Boolean
}
