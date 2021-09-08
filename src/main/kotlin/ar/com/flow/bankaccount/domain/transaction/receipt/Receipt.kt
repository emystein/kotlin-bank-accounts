package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.positive
import ar.com.flow.money.Money
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Receipt(
    val customer: Customer,
    val dateTime: LocalDateTime,
    val movement: FundsMovement,
    val action: Action,
    val amount: Balance,
    val resultBalance: Balance,
) {
    companion object {
        fun debit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val balance = negative(amount)

            return Receipt(
                destinationAccount.owner,
                LocalDateTime.now(),
                FundsMovement.Debit,
                action,
                balance,
                destinationAccount.balance.plus(balance)
            )
        }

        fun credit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val positive = positive(amount)

            return Receipt(
                destinationAccount.owner,
                LocalDateTime.now(),
                FundsMovement.Credit,
                action,
                positive,
                destinationAccount.balance.plus(positive)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Receipt

        if (customer != other.customer) return false
        if (dateTime.truncatedTo(ChronoUnit.MILLIS) != other.dateTime.truncatedTo(ChronoUnit.MILLIS)) return false
        if (movement != other.movement) return false
        if (action != other.action) return false
        if (amount != other.amount) return false
        if (resultBalance != other.resultBalance) return false

        return true
    }
}