package ar.com.flow.bankaccount.domain

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.InMemoryStatement
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.withdrawal.CurrentFundsLimit
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.Statement
import ar.com.flow.money.Money

class SavingsAccount(override val owner: Customer, override val currency: String) : BankAccount {

    var withdrawalLimit: WithdrawalLimit = CurrentFundsLimit()

    // TODO inject dependency
    override val statement: Statement = InMemoryStatement(currency)

    override val initialBalance: Balance
        get() = statement.getInitialBalance()
    override val balance: Balance
        get() = statement.getCurrentBalance()
    override val previousBalance: Balance
        get() = statement.getPreviousBalance()

    override fun withdrawalLimitSupports(amount: Money): Boolean {
        return withdrawalLimit.accepts(this, amount)
    }

    override fun deposit(amountToDeposit: Money) {
        Deposit.to(this)
            .amount(amountToDeposit)
            .execute()
    }

    override fun withdraw(amountToWithdraw: Money) {
        Withdrawal.from(this)
            .amount(amountToWithdraw)
            .execute()
    }

    override fun transfer(amountToTransfer: Money, creditAccount: BankAccount) {
        Transfer.from(this)
            .to(creditAccount)
            .amount(amountToTransfer)
            .execute()
    }

    override fun addReceipt(receipt: Receipt) {
        statement.add(receipt)
    }
}