package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Transfer(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun execute(debitCustomerName: String, currencyCode: String, amountToTransfer: Int, creditCustomerName: String) {
        val currency = Currency.valueOf(currencyCode)

        val (debitAccount, creditAccount) = accountsOwnedBy(debitCustomerName, creditCustomerName, currency = currency)

        debitAccount.transfer(Money(currency, amountToTransfer), creditAccount)

        saveChanges(debitAccount, creditAccount)
    }

    private fun accountsOwnedBy(vararg ownerNames: String, currency: Currency): List<BankAccount> {
        return ownerNames.map { ownerName -> accountOwnedBy(ownerName, currency) }
    }

    private fun accountOwnedBy(ownerName: String, currency: Currency): BankAccount {
        val accountOwner = customers.customerNamed(ownerName).get()
        return bankAccounts.accountOwnedBy(accountOwner, currency).get()
    }

    private fun saveChanges(vararg accounts: BankAccount) {
        accounts.forEach { account -> bankAccounts.save(account) }
    }
}
