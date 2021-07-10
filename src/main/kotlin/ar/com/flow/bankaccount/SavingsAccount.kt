package ar.com.flow.bankaccount

import ar.com.flow.Customer
import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.balance.Balance.Companion.zero
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.bankaccount.withdrawal.CurrentFundsLimit
import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit
import ar.com.flow.money.Money

class SavingsAccount(val owner: Customer, override val currency: String) : BankAccount {

    var withdrawalLimit: WithdrawalLimit = CurrentFundsLimit()

    override val statement: Statement = InMemoryStatement()

    override val initialBalance: Balance
        get() = statement.initialBalance.orElse(zeroBalance())
    override val balance: Balance
        get() = statement.currentBalance.orElse(zeroBalance())
    override val previousBalance: Balance
        get() = statement.previousBalance.orElse(zeroBalance())

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

    private fun zeroBalance(): Balance {
        return zero(currency)
    }
}