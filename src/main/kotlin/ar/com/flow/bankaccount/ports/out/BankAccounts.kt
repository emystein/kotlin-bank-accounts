package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.BankAccount
import java.util.*

interface BankAccounts {
    fun create(owner: Customer, currency: Currency): BankAccount

    fun save(account: BankAccount): BankAccount

    fun accountOwnedBy(owner: Customer, currency: Currency): Optional<BankAccount>

    fun contains(account: BankAccount): Boolean
}
