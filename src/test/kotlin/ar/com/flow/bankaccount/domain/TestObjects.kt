package ar.com.flow.bankaccount.domain

import ar.com.flow.Customer
import ar.com.flow.Customer.Companion.named
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryStatement
import ar.com.flow.bankaccount.domain.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.withdrawal.LowerLimit
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.money.Money
import ar.com.flow.money.TestMoney

object TestObjects {
    var daniel = named("Daniel")
    var mabel = named("Mabel")
    var minusDollars100Limit = LowerLimit(negative(TestMoney.dollars100))
    
    fun createSavingsAccountFor(accountOwner: Customer, initialBalance: Money, statement: ar.com.flow.bankaccount.ports.out.Statement): BankAccount {
        val account = SavingsAccount(accountOwner, Currency.USD, statement)
        account.deposit(initialBalance)
        return account
    }

    fun createCheckingAccountFor(accountOwner: Customer, initialBalance: Money, withdrawalLimit: WithdrawalLimit): BankAccount {
        val account = SavingsAccount(accountOwner, Currency.USD, InMemoryStatement(Currency.USD))
        account.withdrawalLimit = withdrawalLimit
        account.deposit(initialBalance)
        return account
    }
}