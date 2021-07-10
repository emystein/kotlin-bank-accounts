package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.balance.Balance.Companion.positive
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.FundsMovement
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.money.Money
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions

class TransactionRecordAssert(receipt: Receipt) :
    AbstractAssert<TransactionRecordAssert, Receipt>(receipt, TransactionRecordAssert::class.java) {
    
    fun hasMovement(expected: FundsMovement): TransactionRecordAssert {
        Assertions.assertThat(actual.movement).isEqualTo(expected)
        return this
    }

    fun hasAction(expected: Action): TransactionRecordAssert {
        Assertions.assertThat(actual.action).isEqualTo(expected)
        return this
    }

    fun hasBalance(expected: Balance): TransactionRecordAssert {
        Assertions.assertThat(actual.amount).isEqualTo(expected)
        return this
    }

    fun hasCreditAccount(expected: BankAccount): TransactionRecordAssert {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual.destinationAccount)
        return this
    }

    fun isDebit(): TransactionRecordAssert {
        hasMovement(FundsMovement.Debit)
        return this
    }

    fun isCredit(): TransactionRecordAssert {
        hasMovement(FundsMovement.Credit)
        return this
    }

    fun isDeposit(): TransactionRecordAssert {
        hasAction(Action.Deposit)
        return this
    }

    fun isWithdrawal(): TransactionRecordAssert {
        hasAction(Action.Withdrawal)
        return this
    }

    fun isTransfer(): TransactionRecordAssert {
        hasAction(Action.Transfer)
        return this
    }

    fun hasPositiveBalance(amount: Money): TransactionRecordAssert {
        hasBalance(positive(amount))
        return this
    }

    fun hasNegativeBalance(amount: Money): TransactionRecordAssert {
        hasBalance(negative(amount))
        return this
    }

    fun hasResultBalance(balance: Balance): TransactionRecordAssert {
        Assertions.assertThat(actual.resultBalance).isEqualTo(balance)
        return this
    }

    companion object {
        fun assertThat(statement: Receipt): TransactionRecordAssert {
            return TransactionRecordAssert(statement)
        }
    }
}