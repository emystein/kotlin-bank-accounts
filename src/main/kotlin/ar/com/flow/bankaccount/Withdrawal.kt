package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.Builder
import ar.com.flow.bankaccount.transaction.steps.Step
import ar.com.flow.bankaccount.transaction.Transaction
import ar.com.flow.bankaccount.transaction.receipt.*
import ar.com.flow.bankaccount.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Withdrawal {
    fun from(debitAccount: BankAccount): WithdrawalBuilder {
        return WithdrawalBuilder(debitAccount)
    }

    fun receipt(account: BankAccount): ReceiptPrint {
        return DebitPrint(account, Action.Withdrawal)
    }

    fun scratch(account: BankAccount): ReceiptScratch {
        return DebitScratch(account, Action.Withdrawal)
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
                .step(Step(debitAccount, receipt(debitAccount), scratch(debitAccount)))
                .build()
        }
    }
}