package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.Builder
import ar.com.flow.bankaccount.transaction.steps.Step
import ar.com.flow.bankaccount.transaction.Transaction
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.CreditPrinter
import ar.com.flow.bankaccount.transaction.receipt.DebitPrinter
import ar.com.flow.bankaccount.transfer.DifferentAccounts
import ar.com.flow.bankaccount.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Transfer {
    fun from(debitAccount: BankAccount): BankTransferBuilder {
        return BankTransferBuilder(debitAccount)
    }

    fun debitReceipt(account: BankAccount): DebitPrinter {
        return DebitPrinter(account, Action.Transfer)
    }

    fun creditReceipt(account: BankAccount): CreditPrinter {
        return CreditPrinter(account, Action.Transfer)
    }

    internal class BankTransferBuilder(
        private val debitAccount: BankAccount
    ) {
        private lateinit var creditAccount: BankAccount

        fun to(creditAccount: BankAccount): BankTransferBuilder {
            this.creditAccount = creditAccount
            return this
        }

        fun amount(amountToTransfer: Money): Transaction {
            return Builder()
                .precondition(
                    SufficientFunds(
                        debitAccount,
                        amountToTransfer
                    )
                )
                .precondition(
                    DifferentAccounts(
                        debitAccount,
                        creditAccount
                    )
                )
                .step(Step(debitAccount, debitReceipt(creditAccount)))
                .step(Step(creditAccount, creditReceipt(creditAccount)))
                .amount(amountToTransfer)
                .build()
        }
    }
}