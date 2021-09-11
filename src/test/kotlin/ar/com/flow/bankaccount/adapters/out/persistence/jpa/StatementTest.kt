package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.application.spring.BankAccountApplication
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.bankaccount.ports.out.Receipts
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars20
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BankAccountApplication::class])
class StatementTest {
    @Autowired
    private lateinit var accountRegistry: BankAccountRegistry

    @Autowired
    private lateinit var receipts: Receipts

    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    private lateinit var statement: Statement

    private lateinit var dollars10DepositReceipt: Receipt
    private lateinit var dollars10WithdrawReceipt: Receipt
    private lateinit var minusDollars20Record: Receipt

    @BeforeEach
    fun setUp() {
        receiptRepository.deleteAll()

        statement = Statement(daniel, Currency.USD, receipts)

        val danielsAccount = accountRegistry.createSavingsAccount(daniel, Currency.USD)

        dollars10DepositReceipt = credit(danielsAccount, Action.Deposit, dollars10)
        dollars10WithdrawReceipt = debit(danielsAccount, Action.Withdrawal, dollars10)
        minusDollars20Record = debit(danielsAccount, Action.Withdrawal, dollars20)
    }

    @Test
    fun firstDoNotExistWhenStatementIsEmpty() {
        assertThat(statement.first()).isEmpty
    }

    @Test
    fun first() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.first()).hasValue(dollars10DepositReceipt)
    }

    @Test
    fun lastDoNotExistWhenStatementIsEmpty() {
        assertThat(statement.last()).isEmpty
    }

    @Test
    fun last() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.last()).hasValue(dollars10DepositReceipt)
    }

    @Test
    fun containsAllInOrderOfAddition() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertTrue(statement.containsInOrder(dollars10DepositReceipt, dollars10WithdrawReceipt))
    }

    @Test
    fun containsFirstInOrderOfAddition() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertTrue(statement.containsInOrder(dollars10DepositReceipt))
    }

    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(dollars10DepositReceipt)

        assertTrue(statement.contains(dollars10DepositReceipt))
    }
}