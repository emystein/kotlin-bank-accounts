package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers

class CreateSavingsAccount(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun execute(customerName: String, currencyCode: String): BankAccount {
        val customer = customers.save(Customer(customerName))

        return bankAccounts.create(customer, Currency.valueOf(currencyCode))
    }
}
