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

class ReceiptAssert(receipt: Receipt) :
    AbstractAssert<ReceiptAssert, Receipt>(receipt, ReceiptAssert::class.java) {
    
    fun hasMovement(expected: FundsMovement): ReceiptAssert {
        Assertions.assertThat(actual.movement).isEqualTo(expected)
        return this
    }

    fun hasAction(expected: Action): ReceiptAssert {
        Assertions.assertThat(actual.action).isEqualTo(expected)
        return this
    }

    fun hasBalance(expected: Balance): ReceiptAssert {
        Assertions.assertThat(actual.amount).isEqualTo(expected)
        return this
    }

    fun hasCreditAccount(expected: BankAccount): ReceiptAssert {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual.destinationAccount)
        return this
    }

    fun isDebit(): ReceiptAssert {
        hasMovement(FundsMovement.Debit)
        return this
    }

    fun isCredit(): ReceiptAssert {
        hasMovement(FundsMovement.Credit)
        return this
    }

    fun isDeposit(): ReceiptAssert {
        hasAction(Action.Deposit)
        return this
    }

    fun isWithdrawal(): ReceiptAssert {
        hasAction(Action.Withdrawal)
        return this
    }

    fun isTransfer(): ReceiptAssert {
        hasAction(Action.Transfer)
        return this
    }

    fun hasPositiveBalance(amount: Money): ReceiptAssert {
        hasBalance(positive(amount))
        return this
    }

    fun hasNegativeBalance(amount: Money): ReceiptAssert {
        hasBalance(negative(amount))
        return this
    }

    fun hasResultBalance(balance: Balance): ReceiptAssert {
        Assertions.assertThat(actual.resultBalance).isEqualTo(balance)
        return this
    }

    companion object {
        fun assertThat(statement: Receipt): ReceiptAssert {
            return ReceiptAssert(statement)
        }
    }
}