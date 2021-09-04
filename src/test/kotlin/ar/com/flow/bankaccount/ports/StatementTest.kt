package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryStatement
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.domain.TestObjects.francisco
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.negative
import ar.com.flow.bankaccount.domain.balance.Balance.Companion.positive
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.Dollars.Companion.amount
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars100
import ar.com.flow.money.TestObjects.dollars20
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class StatementTest {
    private val currency = "USD"
    private val zeroBalance = Balance.zero(currency)

    private lateinit var franciscosAccount: BankAccount
    private lateinit var statement: Statement
    private lateinit var dollars10DepositReceipt: Receipt
    private lateinit var dollars10WithdrawReceipt: Receipt
    private lateinit var minusDollars20Record: Receipt

    @BeforeEach
    fun setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100)
        dollars10DepositReceipt = credit(franciscosAccount, Action.Deposit, dollars10)
        dollars10WithdrawReceipt = debit(franciscosAccount, Action.Withdrawal, dollars10)
        minusDollars20Record = debit(franciscosAccount, Action.Withdrawal, dollars20)

        statement = InMemoryStatement(currency)
    }

    @Test
    fun newStatementIsEmpty() {
        assertEquals(Optional.empty<Any>(), statement.first())
        assertEquals(zeroBalance, statement.getInitialBalance())
        assertEquals(zeroBalance, statement.getCurrentBalance())
        assertEquals(zeroBalance, statement.getPreviousBalance())
    }

    @Test
    fun first() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.first()).hasValue(dollars10DepositReceipt)
    }

    @Test
    fun firstDoNotExist() {
        assertThat(statement.first()).isEmpty
    }

    @Test
    fun last() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.last()).hasValue(dollars10DepositReceipt)
    }

    @Test
    fun lastDoNotExist() {
        assertThat(statement.last()).isEmpty
    }

    @Test
    fun containsInOrderAll() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertTrue(statement.containsInOrder(dollars10DepositReceipt, dollars10WithdrawReceipt))
    }

    @Test
    fun containsInOrderFirst() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertTrue(statement.containsInOrder(dollars10DepositReceipt))
    }

    @Test
    fun notContainsInOrderSameReceiptsDifferentOrder() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertFalse(statement.containsInOrder(dollars10WithdrawReceipt, dollars10DepositReceipt))
    }
    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(dollars10DepositReceipt)

        assertTrue(statement.contains(dollars10DepositReceipt))
    }

    @Test
    fun statementStoresTransactionRecordsInOrderOfAddition() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertTrue(statement.containsInOrder(dollars10DepositReceipt, dollars10WithdrawReceipt))
    }

    @Test
    fun statementUpdatesBalance() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertEquals(positive(amount(10)), statement.getInitialBalance())
        assertEquals(positive(amount(0)), statement.getCurrentBalance())
        assertEquals(positive(amount(10)), statement.getPreviousBalance())
    }

    @Test
    fun balanceSumsUpCreditAndDebitTransactionRecords() {
        statement.add(dollars10DepositReceipt)
        statement.add(minusDollars20Record)

        assertEquals(negative(dollars10), statement.getCurrentBalance())
    }
}