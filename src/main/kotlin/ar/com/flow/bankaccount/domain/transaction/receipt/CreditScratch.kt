package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class DepositCreditScratch(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.debitDeposit(account, amount)
    }
}

class TransferCreditScratch(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.debitTransfer(account, amount)
    }
}
