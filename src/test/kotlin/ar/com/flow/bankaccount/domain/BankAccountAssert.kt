package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.positive
import ar.com.flow.money.Money
import org.assertj.core.api.AbstractAssert
import org.junit.jupiter.api.Assertions.assertEquals

class BankAccountAssert(account: BankAccount) :
    AbstractAssert<BankAccountAssert, BankAccount>(account, BankAccountAssert::class.java) {
    fun keepsInitialBalance(): BankAccountAssert {
        if (actual.balance != actual.initialBalance) {
            failWithActualExpectedAndMessage(
                actual.balance,
                actual.initialBalance,
                "Balance should be equal to initial balance."
            )
        }
        return this
    }

    fun hasPositiveBalance(amount: Money): BankAccountAssert {
        assertEquals(positive(amount), actual.balance)
        return this
    }

    fun increasedFunds(amount: Money): BankAccountAssert {
        assertEquals(actual.previousBalance.plus(amount), actual.balance)
        return this
    }

    fun decreasedFunds(amount: Money): BankAccountAssert {
        assertEquals(actual.previousBalance.minus(amount), actual.balance)
        return this
    }

    fun hasNegativeBalance(amount: Money): BankAccountAssert {
        assertEquals(negative(amount), actual.balance)
        return this
    }

    companion object {
        fun assertThat(account: BankAccount): BankAccountAssert {
            return BankAccountAssert(account)
        }
    }
}