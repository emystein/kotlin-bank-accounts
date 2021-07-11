package ar.com.flow.bankaccount.transaction.receipt

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.money.Money

class DebitScratch(private val account: BankAccount, private val action: Action) : ReceiptScratch {
    override fun print(amount: Money): Receipt {
        return Receipt.credit(account, action, amount)
    }
}