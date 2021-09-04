package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Deposit(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun deposit(customerName: String, currency: String, amountToDeposit: Int) {
        val customer = customers.customerNamed(customerName) ?: throw CustomerNotFound(customerName)

        val savingsAccount = bankAccounts.accountOwnedBy(customer, currency) ?: throw CurrencyNotUsed(currency)

        return savingsAccount.deposit(Money(currency, amountToDeposit))
    }
}
