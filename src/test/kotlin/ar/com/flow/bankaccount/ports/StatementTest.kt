package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryAccountRegistry
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryStatement
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars20
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StatementTest {
    private lateinit var danielsAccount: BankAccount
    private lateinit var statement: Statement
    private lateinit var deposit10Receipt: Receipt
    private lateinit var withdraw10Receipt: Receipt
    private lateinit var withdraw20Receipt: Receipt

    private val accountRegistry = InMemoryAccountRegistry()

    @BeforeEach
    fun setUp() {
        statement = InMemoryStatement(Currency.USD)

        danielsAccount = accountRegistry.createSavingsAccountFor(daniel, Currency.USD)

        deposit10Receipt = credit(danielsAccount, Action.Deposit, dollars10)
        withdraw10Receipt = debit(danielsAccount, Action.Withdrawal, dollars10)
        withdraw20Receipt = debit(danielsAccount, Action.Withdrawal, dollars20)
    }

    @Test
    fun first() {
        statement.add(deposit10Receipt)

        assertThat(statement.first()).hasValue(deposit10Receipt)
    }

    @Test
    fun firstDoNotExist() {
        assertThat(statement.first()).isEmpty
    }

    @Test
    fun last() {
        statement.add(deposit10Receipt)

        assertThat(statement.last()).hasValue(deposit10Receipt)
    }

    @Test
    fun lastDoNotExist() {
        assertThat(statement.last()).isEmpty
    }

    @Test
    fun containsInOrderAll() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertTrue(statement.containsInOrder(deposit10Receipt, withdraw10Receipt))
    }

    @Test
    fun containsInOrderFirst() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertTrue(statement.containsInOrder(deposit10Receipt))
    }

    @Test
    fun notContainsInOrderSameReceiptsDifferentOrder() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertFalse(statement.containsInOrder(withdraw10Receipt, deposit10Receipt))
    }
    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(deposit10Receipt)

        assertTrue(statement.contains(deposit10Receipt))
    }

    @Test
    fun statementStoresTransactionRecordsInOrderOfAddition() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertTrue(statement.containsInOrder(deposit10Receipt, withdraw10Receipt))
    }
}