package ar.com.flow.bankaccount.domain

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.withdrawal.CurrentFundsLimit
import ar.com.flow.bankaccount.domain.withdrawal.WithdrawalLimit
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.Money

data class SavingsAccount(
    override val owner: Customer,
    override val currency: Currency,
    override val statement: Statement
) : BankAccount {
    var withdrawalLimit: WithdrawalLimit = CurrentFundsLimit()

    override val initialBalance: Balance
        get() = if (statement.first().isPresent) statement.first().get().amount else Balance.zero(currency)

    override val balance: Balance
        get() = statement.sum(statement.count())

    override val previousBalance: Balance
        get() = statement.sum(statement.count() - 1)

    override fun allowsWithdraw(amount: Money): Boolean {
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
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as SavingsAccount

        if (owner != other.owner) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = owner.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }
}