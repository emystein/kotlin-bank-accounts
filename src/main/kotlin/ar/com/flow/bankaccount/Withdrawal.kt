package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.Builder
import ar.com.flow.bankaccount.transaction.Step
import ar.com.flow.bankaccount.transaction.Transaction
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.DebitPrinter
import ar.com.flow.bankaccount.transaction.receipt.ReceiptPrinter
import ar.com.flow.bankaccount.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Withdrawal {
    fun from(debitAccount: BankAccount): WithdrawalBuilder {
        return WithdrawalBuilder(debitAccount)
    }

    fun receipt(account: BankAccount): ReceiptPrinter {
        return DebitPrinter(account, Action.Withdrawal)
    }

    internal class WithdrawalBuilder(private val debitAccount: BankAccount) {
        fun amount(amountToWithdraw: Money): Transaction {
            return Builder()
                .amount(amountToWithdraw)
                .precondition(
                    SufficientFunds(
                        debitAccount,
                        amountToWithdraw
                    )
                )
                .step(Step(debitAccount, receipt(debitAccount)))
                .build()
        }
    }
}