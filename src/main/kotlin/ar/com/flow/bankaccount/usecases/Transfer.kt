package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Transfer(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun transfer(debitCustomerName: String, currency: String, amountToTransfer: Int, creditCustomerName: String) {
        val debitCustomer = customers.customerNamed(debitCustomerName)
        val debitAccount = bankAccounts.accountOwnedBy(debitCustomer, currency)
        val creditCustomer = customers.customerNamed(creditCustomerName)
        val creditAccount = bankAccounts.accountOwnedBy(creditCustomer, currency)
        debitAccount.transfer(Money(debitAccount.currency, amountToTransfer), creditAccount)
        bankAccounts.save(debitAccount)
        bankAccounts.save(creditAccount)
    }
}
