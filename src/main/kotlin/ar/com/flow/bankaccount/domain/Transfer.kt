package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.Builder
import ar.com.flow.bankaccount.domain.transaction.Transaction
import ar.com.flow.bankaccount.domain.transaction.receipt.*
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.bankaccount.domain.withdrawal.SufficientFunds
import ar.com.flow.money.Money

internal object Transfer {
    fun from(debitAccount: BankAccount): BankTransferBuilder {
        return BankTransferBuilder(debitAccount)
    }

    internal class BankTransferBuilder(private val debitAccount: BankAccount) {
        private lateinit var creditAccount: BankAccount

        fun to(creditAccount: BankAccount): BankTransferBuilder {
            this.creditAccount = creditAccount
            return this
        }

        fun amount(amountToTransfer: Money): Transaction {
            return Builder()
                .precondition(SufficientFunds(debitAccount, amountToTransfer))
                .precondition(AccountAreDifferent(debitAccount, creditAccount))
                .step(Step(debitAccount, TransferDebitPrint(creditAccount), TransferDebitScratch(creditAccount)))
                .step(Step(creditAccount, TransferCreditPrint(creditAccount), TransferCreditScratch(creditAccount)))
                .amount(amountToTransfer)
                .build()
        }
    }
}