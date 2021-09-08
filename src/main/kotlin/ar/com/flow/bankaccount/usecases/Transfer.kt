package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Transfer(private val customers: Customers, private val savingsAccounts: SavingsAccounts) {
    fun execute(debitCustomerName: String, currency: String, amountToTransfer: Int, creditCustomerName: String) {
        val (debitAccount, creditAccount) = accountsOwnedBy(debitCustomerName, creditCustomerName, currency = currency)

        debitAccount.transfer(Money(currency, amountToTransfer), creditAccount)

        saveChanges(debitAccount, creditAccount)
    }

    private fun accountsOwnedBy(vararg ownerNames: String, currency: String): List<SavingsAccount> {
        return ownerNames.map { ownerName -> accountOwnedBy(ownerName, currency) }
    }

    private fun accountOwnedBy(ownerName: String, currency: String): SavingsAccount {
        val accountOwner = customers.customerNamed(ownerName).get()
        return savingsAccounts.accountOwnedBy(accountOwner, currency).get()
    }

    private fun saveChanges(vararg accounts: SavingsAccount) {
        accounts.forEach { account -> savingsAccounts.save(account) }
    }
}
