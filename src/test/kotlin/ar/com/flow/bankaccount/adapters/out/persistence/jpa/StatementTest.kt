package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.application.spring.BankAccountApplication
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.creditDeposit
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars20
import assertk.assertThat
import assertk.assertions.hasValue
import assertk.assertions.isEmpty
import assertk.assertions.isTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BankAccountApplication::class])
class StatementTest {
    @Autowired
    private lateinit var bankAccounts: BankAccounts
    @Autowired
    private lateinit var receiptMapper: ReceiptMapper
    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    private lateinit var statement: Statement

    private lateinit var dollars10DepositReceipt: Receipt
    private lateinit var dollars10WithdrawReceipt: Receipt
    private lateinit var minusDollars20Record: Receipt

    @BeforeEach
    fun setUp() {
        receiptRepository.deleteAll()

        val danielsAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        val receipts = AccountReceipts(danielsAccount.id, receiptMapper, receiptRepository)

        statement = Statement(Currency.USD, receipts)

        dollars10DepositReceipt = creditDeposit(danielsAccount, dollars10)
        dollars10WithdrawReceipt = Receipt.debitWithdrawal(danielsAccount, dollars10)
        minusDollars20Record = Receipt.debitWithdrawal(danielsAccount,  dollars20)
    }

    @Test
    fun firstDoNotExistWhenStatementIsEmpty() {
        assertThat(statement.first()).isEmpty()
    }

    @Test
    fun first() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.first()).hasValue(dollars10DepositReceipt)
    }

    @Test
    fun lastDoNotExistWhenStatementIsEmpty() {
        assertThat(statement.last()).isEmpty()
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

        assertThat(statement.containsInOrder(dollars10DepositReceipt, dollars10WithdrawReceipt)).isTrue()
    }

    @Test
    fun containsFirstInOrderOfAddition() {
        statement.add(dollars10DepositReceipt)
        statement.add(dollars10WithdrawReceipt)

        assertThat(statement.containsInOrder(dollars10DepositReceipt)).isTrue()
    }

    @Test
    fun statementContainsTransactionRecordAdded() {
        statement.add(dollars10DepositReceipt)

        assertThat(statement.contains(dollars10DepositReceipt)).isTrue()
    }
}