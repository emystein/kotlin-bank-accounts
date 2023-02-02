package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.transaction.Builder
import ar.com.flow.bankaccount.domain.transaction.Transaction
import ar.com.flow.bankaccount.domain.transaction.receipt.*
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.money.Money

internal object Deposit {
    fun to(creditAccount: BankAccount): DepositBuilder {
        return DepositBuilder(creditAccount)
    }

    fun receipt(account: BankAccount): ReceiptPrint {
        return DepositCreditPrint(account)
    }

    fun scratch(account: BankAccount): ReceiptPrint {
        return DepositCreditScratch(account)
    }

    internal class DepositBuilder(private val creditAccount: BankAccount) {
        fun amount(amountToDeposit: Money): Transaction {
            return Builder()
                .amount(amountToDeposit)
                .step(Step(creditAccount, receipt(creditAccount), scratch(creditAccount)))
                .build()
        }
    }
}