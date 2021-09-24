package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.Balance.Companion.positive
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money
import java.time.LocalDateTime

data class Receipt(
    val accountId: AccountId,
    val dateTime: LocalDateTime,
    val movement: FundsMovement,
    val action: Action,
    val amount: Balance,
) {
    val currency = amount.currency

    companion object {
        fun debit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val balance = negative(amount)

            return Receipt(
                destinationAccount.id,
                LocalDateTime.now(),
                FundsMovement.Debit,
                action,
                balance
            )
        }

        fun credit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val positive = positive(amount)

            return Receipt(
                destinationAccount.id,
                LocalDateTime.now(),
                FundsMovement.Credit,
                action,
                positive
            )
        }
    }
}