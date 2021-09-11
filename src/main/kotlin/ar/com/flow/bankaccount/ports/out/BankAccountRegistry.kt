package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency

interface BankAccountRegistry {
    fun createSavingsAccount(accountOwner: Customer, currency: Currency): BankAccount
}