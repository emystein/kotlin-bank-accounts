package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Withdrawal(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun execute(customerName: String, currencyCode: String, amountToWithdraw: Int) {
        val customer = customers.customerNamed(customerName).get()

        val currency = Currency.valueOf(currencyCode)

        val savingsAccount = bankAccounts.ownedBy(customer, currency).first()

        savingsAccount.withdraw(Money(currency, amountToWithdraw))

        bankAccounts.save(savingsAccount)
    }
}
