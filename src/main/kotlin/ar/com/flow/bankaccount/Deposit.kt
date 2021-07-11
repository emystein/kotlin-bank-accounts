package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.Builder
import ar.com.flow.bankaccount.transaction.Step
import ar.com.flow.bankaccount.transaction.Transaction
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.CreditPrinter
import ar.com.flow.money.Money

internal object Deposit {
    fun to(creditAccount: BankAccount): DepositBuilder {
        return DepositBuilder(creditAccount)
    }

    fun receipt(account: BankAccount?): CreditPrinter {
        return CreditPrinter(account!!, Action.Deposit)
    }

    internal class DepositBuilder(private val creditAccount: BankAccount) {
        fun amount(amountToDeposit: Money): Transaction {
            return Builder()
                .amount(amountToDeposit)
                .step(Step(creditAccount, receipt(creditAccount)))
                .build()
        }
    }
}