package ar.com.flow.bankaccount.transaction.steps

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.bankaccount.transaction.receipt.ReceiptPrinter
import ar.com.flow.money.Money

class Step(private val account: BankAccount, private val receiptPrinter: ReceiptPrinter) {
    fun execute(amount: Money): Receipt {
        val receipt = receiptPrinter.print(amount)
        account.addReceipt(receipt)
        return receipt
    }

    fun revoke(amount: Money): Receipt {
        val receipt = receiptPrinter.scratch(amount)
        account.addReceipt(receipt)
        return receipt
    }
}
