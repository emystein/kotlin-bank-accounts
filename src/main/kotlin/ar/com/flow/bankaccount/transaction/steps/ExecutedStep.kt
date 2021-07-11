package ar.com.flow.bankaccount.transaction.steps

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.transaction.receipt.ReceiptScratch
import ar.com.flow.money.Money

class ExecutedStep(
    private val account: BankAccount,
    private val amount: Money,
    private val receiptScratch: ReceiptScratch
) {
    fun undo() {
        val scratchedReceipt = receiptScratch.print(amount)

        account.addReceipt(scratchedReceipt)
    }
}
