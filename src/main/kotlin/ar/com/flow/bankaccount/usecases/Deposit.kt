package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Deposit(private val customers: Customers, private val savingsAccounts: SavingsAccounts) {
    fun deposit(customerName: String, currency: String, amountToDeposit: Int) {
        val customer = customers.customerNamed(customerName)

        val savingsAccount = savingsAccounts.accountOwnedBy(customer, currency)

        savingsAccount.deposit(Money(currency, amountToDeposit))

        savingsAccounts.save(savingsAccount)
    }
}
