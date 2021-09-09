package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import java.util.*

interface BankAccounts {
    fun create(owner: Customer, currency: String): BankAccount

    fun save(account: BankAccount)

    fun accountOwnedBy(accountOwner: Customer, currency: String): Optional<BankAccount>

    fun contains(account: BankAccount): Boolean
}
