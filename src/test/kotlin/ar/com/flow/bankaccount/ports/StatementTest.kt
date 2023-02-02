package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryStatement
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.creditDeposit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debitWithdrawal
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars20
import assertk.assertThat
import assertk.assertions.hasValue
import assertk.assertions.isEmpty
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StatementTest {
    private lateinit var danielsAccount: BankAccount
    private lateinit var statement: Statement
    private lateinit var deposit10Receipt: Receipt
    private lateinit var withdraw10Receipt: Receipt
    private lateinit var withdraw20Receipt: Receipt

    private val bankAccounts: BankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    @BeforeEach
    fun setUp() {
        statement = InMemoryStatement(Currency.USD)

        danielsAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        deposit10Receipt = creditDeposit(danielsAccount, dollars10)
        withdraw10Receipt = debitWithdrawal(danielsAccount, dollars10)
        withdraw20Receipt = debitWithdrawal(danielsAccount, dollars20)
    }

    @Test
    fun first() {
        statement.add(deposit10Receipt)

        assertThat(statement.first()).hasValue(deposit10Receipt)
    }

    @Test
    fun firstDoNotExist() {
        assertThat(statement.first()).isEmpty()
    }

    @Test
    fun last() {
        statement.add(deposit10Receipt)

        assertThat(statement.last()).hasValue(deposit10Receipt)
    }

    @Test
    fun lastDoNotExist() {
        assertThat(statement.last()).isEmpty()
    }

    @Test
    fun containsInOrderAll() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertThat(statement.containsInOrder(deposit10Receipt, withdraw10Receipt)).isTrue()
    }

    @Test
    fun containsInOrderFirst() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertThat(statement.containsInOrder(deposit10Receipt)).isTrue()
    }

    @Test
    fun notContainsInOrderSameReceiptsDifferentOrder() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertThat(statement.containsInOrder(withdraw10Receipt, deposit10Receipt)).isFalse()
    }
    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(deposit10Receipt)

        assertThat(statement.contains(deposit10Receipt)).isTrue()
    }

    @Test
    fun statementStoresTransactionRecordsInOrderOfAddition() {
        statement.add(deposit10Receipt)
        statement.add(withdraw10Receipt)

        assertThat(statement.containsInOrder(deposit10Receipt, withdraw10Receipt)).isTrue()
    }
}