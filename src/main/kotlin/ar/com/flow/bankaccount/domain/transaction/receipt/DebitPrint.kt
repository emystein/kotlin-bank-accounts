package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class DebitPrint(private val account: BankAccount, private val action: Action) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.debit(account, action, amount)
    }
}