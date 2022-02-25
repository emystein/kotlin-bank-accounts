package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit

interface BankAccounts {
    fun createSavingsAccount(accountOwner: Customer, currency: Currency): BankAccount

    fun createCheckingAccount(accountOwner: Customer, currency: Currency, withdrawalLimit: WithdrawalLimit): BankAccount

    fun save(account: BankAccount): BankAccount

    fun ownedBy(accountOwner: Customer): List<BankAccount>

    fun ownedBy(accountOwner: Customer, currency: Currency): List<BankAccount>

    fun contains(account: BankAccount): Boolean
}
