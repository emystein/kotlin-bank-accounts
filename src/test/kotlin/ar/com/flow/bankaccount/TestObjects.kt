package ar.com.flow.bankaccount

import ar.com.flow.Customer
import ar.com.flow.Customer.Companion.named
import ar.com.flow.bankaccount.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.withdrawal.LowerLimit
import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit
import ar.com.flow.money.Money
import ar.com.flow.money.TestObjects

object TestObjects {
    var francisco = named("francisco")
    var mabel = named("mabel")
    var minusDollars100Limit = LowerLimit(negative(TestObjects.dollars100))
    
    fun createSavingsAccountFor(accountOwner: Customer, initialBalance: Money): BankAccount {
        val account = SavingsAccount(accountOwner, "USD")
        account.deposit(initialBalance)
        return account
    }

    fun createCheckingAccountFor(accountOwner: Customer, initialBalance: Money, withdrawalLimit: WithdrawalLimit): BankAccount {
        val account = SavingsAccount(accountOwner, "USD")
        account.withdrawalLimit = withdrawalLimit
        account.deposit(initialBalance)
        return account
    }
}