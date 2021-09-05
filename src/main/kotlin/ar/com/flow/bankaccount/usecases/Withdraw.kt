package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Withdraw(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun withdraw(customerName: String, currency: String, amountToWithdraw: Int) {
        val customer = customers.customerNamed(customerName)

        val savingsAccount = bankAccounts.accountOwnedBy(customer, currency)

        savingsAccount.withdraw(Money(currency, amountToWithdraw))

        bankAccounts.save(savingsAccount)
    }
}
