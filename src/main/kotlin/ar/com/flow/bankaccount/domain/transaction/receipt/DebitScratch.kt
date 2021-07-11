package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class DebitScratch(private val account: BankAccount, private val action: Action) : ReceiptScratch {
    override fun print(amount: Money): Receipt {
        return Receipt.credit(account, action, amount)
    }
}