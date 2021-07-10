package ar.com.flow.bankaccount.transaction.receipt

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.money.Money

class DebitPrinter(
    private val account: BankAccount,
    private val action: Action
) : ReceiptPrinter {

    override fun print(amount: Money): Receipt {
        return Receipt.Companion.debit(account, action, amount)
    }

    override fun scratch(amount: Money): Receipt {
        return Receipt.Companion.credit(account, action, amount)
    }
}