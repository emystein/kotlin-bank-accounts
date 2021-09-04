package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers

class CreateSavingsAccount(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun createAccount(customerName: String, currency: String): SavingsAccount {
        val customer = Customer(customerName)
        val savedCustomer = customers.save(customer)
        return bankAccounts.create(savedCustomer, currency)
    }
}
