package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Deposit(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun execute(customerName: String, currency: String, amountToDeposit: Int) {
        val customer = customers.customerNamed(customerName).get()

        val savingsAccount = bankAccounts.accountOwnedBy(customer, currency).get()

        savingsAccount.deposit(Money(currency, amountToDeposit))

        bankAccounts.save(savingsAccount)
    }
}
