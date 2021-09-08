package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Withdraw(private val customers: Customers, private val savingsAccounts: SavingsAccounts) {
    fun execute(customerName: String, currency: String, amountToWithdraw: Int) {
        val customer = customers.customerNamed(customerName)

        val savingsAccount = savingsAccounts.accountOwnedBy(customer, currency).get()

        savingsAccount.withdraw(Money(currency, amountToWithdraw))

        savingsAccounts.save(savingsAccount)
    }
}
