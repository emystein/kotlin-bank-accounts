package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money

class WithdrawalDebitScratch(private val account: BankAccount) : ReceiptScratch {
    override fun print(amount: Money): Receipt {
        return Receipt.creditWithdrawal(account, amount)
    }
}

class TransferDebitScratch(private val account: BankAccount) : ReceiptScratch {
    override fun print(amount: Money): Receipt {
        return Receipt.creditTransfer(account, amount)
    }
}