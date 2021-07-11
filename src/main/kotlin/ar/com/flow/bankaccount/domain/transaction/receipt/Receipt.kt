package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.positive
import ar.com.flow.money.Money
import java.time.LocalDateTime

class Receipt(
    val destinationAccount: BankAccount,
    val dateTime: LocalDateTime,
    val movement: FundsMovement,
    val action: Action,
    val amount: Balance,
    val resultBalance: Balance,
) {
    companion object {
        @JvmStatic
        fun debit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val balance = negative(amount)

            return Receipt(
                destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Debit,
                action,
                balance,
                destinationAccount.balance.plus(balance)
            )
        }

        @JvmStatic
        fun credit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val positive = positive(amount)

            return Receipt(
                destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Credit,
                action,
                positive,
                destinationAccount.balance.plus(positive)
            )
        }
    }
}