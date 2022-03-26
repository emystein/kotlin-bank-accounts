package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import java.util.*

interface BankAccounts {
    // TODO model new account form holding information of owner and currency
    fun createSavingsAccount(owner: Customer, currency: Currency): BankAccount

    // TODO model new account form holding information of owner, currency and withdrawal limit
    fun createCheckingAccount(owner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount

    fun getById(accountId: AccountId): Optional<BankAccount>

    fun save(account: BankAccount): BankAccount

    fun all(): List<BankAccount>

    fun ownedBy(accountOwner: Customer): List<BankAccount>

    // TODO model BankAccountQuery
    fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount>

    fun contains(account: BankAccount): Boolean

    fun query(filters: BankAccountFilters): List<BankAccount>
}
