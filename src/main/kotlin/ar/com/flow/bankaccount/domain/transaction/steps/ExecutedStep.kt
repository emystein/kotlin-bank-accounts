package ar.com.flow.bankaccount.domain.transaction.steps

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.transaction.receipt.ReceiptPrint
import ar.com.flow.money.Money

class ExecutedStep(
    private val account: BankAccount,
    private val amount: Money,
    private val receiptScratch: ReceiptPrint
) {
    fun undo() {
        val scratchedReceipt = receiptScratch.print(amount)

        account.addReceipt(scratchedReceipt)
    }
}
