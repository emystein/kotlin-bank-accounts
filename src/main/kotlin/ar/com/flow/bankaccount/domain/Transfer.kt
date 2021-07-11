package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.Builder
import ar.com.flow.bankaccount.domain.transaction.Transaction
import ar.com.flow.bankaccount.domain.transaction.receipt.*
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.bankaccount.domain.transfer.DifferentAccounts
import ar.com.flow.bankaccount.domain.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Transfer {
    fun from(debitAccount: BankAccount): BankTransferBuilder {
        return BankTransferBuilder(debitAccount)
    }

    fun debitReceipt(account: BankAccount): DebitPrint {
        return DebitPrint(account, Action.Transfer)
    }

    fun debitScratch(account: BankAccount): DebitScratch {
        return DebitScratch(account, Action.Transfer)
    }

    fun creditReceipt(account: BankAccount): CreditPrint {
        return CreditPrint(account, Action.Transfer)
    }

    fun creditScratch(account: BankAccount): CreditScratch {
        return CreditScratch(account, Action.Transfer)
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
                .step(Step(debitAccount, debitReceipt(creditAccount), debitScratch(creditAccount)))
                .step(Step(creditAccount, creditReceipt(creditAccount), creditScratch(creditAccount)))
                .amount(amountToTransfer)
                .build()
        }
    }
}