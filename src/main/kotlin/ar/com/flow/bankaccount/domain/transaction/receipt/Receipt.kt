package ar.com.flow.bankaccount.domain.transaction.receipt

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.Balance.Companion.positive
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.money.Money
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneOffset

data class Receipt(
    val accountId: AccountId,
    val dateTime: LocalDateTime,
    val movement: FundsMovement,
    val action: Action,
    val amount: Balance,
) {
    val currency = amount.currency

    companion object {
        fun debitDeposit(destinationAccount: BankAccount, amount: Money): Receipt {
            return debit(destinationAccount, Action.Deposit, amount)
        }

        fun debitWithdrawal(destinationAccount: BankAccount, amount: Money): Receipt {
            return debit(destinationAccount, Action.Withdrawal, amount)
        }

        fun debitTransfer(destinationAccount: BankAccount, amount: Money): Receipt {
            return debit(destinationAccount, Action.Transfer, amount)
        }

        fun creditDeposit(destinationAccount: BankAccount, amount: Money): Receipt {
            return credit(destinationAccount, Action.Deposit, amount)
        }

        fun creditWithdrawal(destinationAccount: BankAccount, amount: Money): Receipt {
            return credit(destinationAccount, Action.Withdrawal, amount)
        }

        fun creditTransfer(destinationAccount: BankAccount, amount: Money): Receipt {
            return credit(destinationAccount, Action.Transfer, amount)
        }

        private fun debit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val balance = negative(amount)

            return Receipt(destinationAccount.id, now(), FundsMovement.Debit, action, balance)
        }

        private fun credit(destinationAccount: BankAccount, action: Action, amount: Money): Receipt {
            val positive = positive(amount)

            return Receipt(destinationAccount.id, now(), FundsMovement.Credit, action, positive)
        }
    }

    fun isDeposit(): Boolean {
        return action == Action.Deposit
    }

    fun isWithdrawal(): Boolean {
        return action == Action.Withdrawal
    }

    fun isTransfer(): Boolean {
        return action == Action.Transfer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Receipt

        if (accountId != other.accountId) return false
        // FIXME: Java 16 LocalDateTime equals fails due to millis truncation then a workaround is to compare with seconds precision
        if (dateTime.toEpochSecond(ZoneOffset.UTC) != other.dateTime.toEpochSecond((ZoneOffset.UTC))) return false
        if (movement != other.movement) return false
        if (amount != other.amount) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accountId.hashCode()
        result = 31 * result + dateTime.hashCode()
        result = 31 * result + movement.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }
}