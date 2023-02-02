package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class WithdrawalDebitPrint(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.debitWithdrawal(account, amount)
    }
}

class TransferDebitPrint(private val account: BankAccount) : ReceiptPrint {
    override fun print(amount: Money): Receipt {
        return Receipt.debitTransfer(account, amount)
    }
}
