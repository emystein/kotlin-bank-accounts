package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.money.Money

class Transfer(private val customers: Customers, private val bankAccounts: BankAccounts) {
    fun transfer(debitCustomerName: String, currency: String, amountToTransfer: Int, creditCustomerName: String) {
        val (debitAccount, creditAccount) = accountsOwnedBy(debitCustomerName, creditCustomerName, currency = currency)

        debitAccount.transfer(Money(currency, amountToTransfer), creditAccount)

        saveChanges(debitAccount, creditAccount)
    }

    private fun accountsOwnedBy(vararg ownerNames: String, currency: String): List<SavingsAccount> {
        return ownerNames.map { ownerName -> accountOwnedBy(ownerName, currency) }
    }

    private fun accountOwnedBy(ownerName: String, currency: String): SavingsAccount {
        val accountOwner = customers.customerNamed(ownerName)
        return bankAccounts.accountOwnedBy(accountOwner, currency)
    }

    private fun saveChanges(vararg accounts: SavingsAccount) {
        accounts.forEach { account -> bankAccounts.save(account) }
    }
}
