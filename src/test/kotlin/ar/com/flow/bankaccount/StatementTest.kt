package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.TestObjects.francisco
import ar.com.flow.bankaccount.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.balance.Balance.Companion.positive
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.Receipt
import ar.com.flow.bankaccount.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.money.Dollars.Companion.amount
import ar.com.flow.money.TestObjects
import ar.com.flow.money.TestObjects.dollars10
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class StatementTest {
    private lateinit var franciscosAccount: BankAccount
    private lateinit var statement: Statement
    private lateinit var dollars10Record: Receipt
    private lateinit var dollars20Record: Receipt
    private lateinit var minusDollars20Record: Receipt

    @BeforeEach
    fun setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, TestObjects.dollars100)
        dollars10Record = credit(franciscosAccount, Action.Deposit, dollars10)
        dollars20Record = credit(franciscosAccount, Action.Deposit, TestObjects.dollars20)
        minusDollars20Record = debit(franciscosAccount, Action.Withdrawal, TestObjects.dollars20)
        statement = InMemoryStatement()
    }

    @Test
    fun newStatementIsEmpty() {
        assertEquals(Optional.empty<Any>(), statement.first())
        assertEquals(Optional.empty<Any>(), statement.initialBalance)
        assertEquals(Optional.empty<Any>(), statement.currentBalance)
        assertEquals(Optional.empty<Any>(), statement.previousBalance)
    }

    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(dollars10Record)

        assertTrue(statement.contains(dollars10Record))
    }

    @Test
    fun statementStoresTransactionRecordsInOrderOfAddition() {
        statement.add(dollars10Record)
        statement.add(dollars20Record)

        assertTrue(statement.containsInOrder(dollars10Record, dollars20Record))
    }

    @Test
    fun statementUpdatesBalance() {
        statement.add(dollars10Record)
        statement.add(dollars20Record)

        assertEquals(Optional.of(positive(amount(10))), statement.initialBalance)
        assertEquals(Optional.of(positive(amount(30))), statement.currentBalance)
        assertEquals(Optional.of(positive(amount(10))), statement.previousBalance)
    }

    @Test
    fun balanceSumsUpCreditAndDebitTransactionRecords() {
        statement.add(dollars10Record)
        statement.add(minusDollars20Record)

        assertEquals(Optional.of(negative(dollars10)), statement.currentBalance)
    }
}