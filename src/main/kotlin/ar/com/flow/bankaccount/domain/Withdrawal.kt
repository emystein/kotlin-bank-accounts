package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.Builder
import ar.com.flow.bankaccount.domain.transaction.Transaction
import ar.com.flow.bankaccount.domain.transaction.receipt.*
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.bankaccount.domain.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Withdrawal {
    fun from(debitAccount: BankAccount): WithdrawalBuilder {
        return WithdrawalBuilder(debitAccount)
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
                .step(Step(debitAccount, WithdrawalDebitPrint(debitAccount), WithdrawalDebitScratch(debitAccount)))
                .build()
        }
    }
}