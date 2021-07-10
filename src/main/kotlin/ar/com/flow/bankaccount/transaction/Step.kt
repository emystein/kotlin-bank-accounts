package ar.com.flow.bankaccount.transaction

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.bankaccount.transaction.receipt.ReceiptPrinter
import ar.com.flow.money.Money

class Step(
    private val account: BankAccount,
    private val receiptPrinter: ReceiptPrinter
) {
    fun execute(amount: Money): Receipt {
        return register(receiptPrinter.print(amount))
    }

    fun undo(amount: Money): Receipt {
        return register(receiptPrinter.scratch(amount))
    }

    private fun register(receipt: Receipt): Receipt {
        account.addReceipt(receipt)
        return receipt
    }
}