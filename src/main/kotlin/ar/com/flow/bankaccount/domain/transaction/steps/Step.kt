package ar.com.flow.bankaccount.domain.transaction.steps

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.transaction.receipt.ReceiptPrint
import ar.com.flow.bankaccount.domain.transaction.receipt.ReceiptScratch
import ar.com.flow.money.Money

class Step(
    private val account: BankAccount,
    private val receiptPrint: ReceiptPrint,
    private val receiptScratch: ReceiptScratch
) {
    fun execute(amount: Money): ExecutedStep {
        val receipt = receiptPrint.print(amount)
        account.addReceipt(receipt)
        return ExecutedStep(account, amount, receiptScratch)
    }
}
