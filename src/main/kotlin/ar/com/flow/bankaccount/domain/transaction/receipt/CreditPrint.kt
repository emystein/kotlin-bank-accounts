package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class DepositCreditPrint(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.creditDeposit(account, amount)
    }
}

class TransferCreditPrint(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.creditTransfer(account, amount)
    }
}
