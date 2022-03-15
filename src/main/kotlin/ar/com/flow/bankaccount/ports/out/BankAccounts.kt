package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import java.util.*

interface BankAccounts {
    fun createSavingsAccount(owner: Customer, currency: Currency): BankAccount

    fun createCheckingAccount(owner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount

    fun getById(accountId: AccountId): Optional<BankAccount>

    fun save(account: BankAccount): BankAccount

    fun ownedBy(accountOwner: Customer): List<BankAccount>

    fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount>

    fun contains(account: BankAccount): Boolean
}
