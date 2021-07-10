package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.TestObjects.francisco
import ar.com.flow.bankaccount.balance.Balance
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars100
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InMemoryStatementTest {
    private lateinit var franciscosAccount: BankAccount
    private lateinit var statement: Statement

    private lateinit var receipt1: Receipt
    private lateinit var receipt2: Receipt

    private val currency = "USD"

    @BeforeEach
    fun setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100)

        statement = InMemoryStatement(currency)

        receipt1 = Receipt.credit(franciscosAccount, Action.Deposit, dollars10)
        receipt2 = Receipt.credit(franciscosAccount, Action.Withdrawal, dollars10)
    }

    @Test
    fun first() {
        statement.add(receipt1)

        assertThat(statement.first()).hasValue(receipt1)
    }

    @Test
    fun firstNotExists() {
        assertThat(statement.first()).isEmpty
    }

    @Test
    fun last() {
        statement.add(receipt1)

        assertThat(statement.last()).hasValue(receipt1)
    }

    @Test
    fun lastNotExists() {
        assertThat(statement.last()).isEmpty
    }

    @Test
    fun containsInOrderAll() {
        statement.add(receipt1)
        statement.add(receipt2)

        assertTrue(statement.containsInOrder(receipt1, receipt2))
    }

    @Test
    fun containsInOrderFirst() {
        statement.add(receipt1)
        statement.add(receipt2)

        assertTrue(statement.containsInOrder(receipt1))
    }

    @Test
    fun notContainsInOrderSameReceiptsDifferentOrder() {
        statement.add(receipt1)
        statement.add(receipt2)

        assertFalse(statement.containsInOrder(receipt2, receipt1))
    }
}