package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.application.spring.BankAccountApplication
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.bankaccount.ports.out.Statement
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars20
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BankAccountApplication::class])
class StatementTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var bankAccountRepository: BankAccountRepository

    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    @Autowired
    private lateinit var savingsAccountMapper: SavingsAccountMapper

    private val dollars = "USD"

    private lateinit var danielsAccount: BankAccount
    private lateinit var statement: Statement
    private lateinit var dollars10DepositReceipt: Receipt
    private lateinit var dollars10WithdrawReceipt: Receipt
    private lateinit var minusDollars20Record: Receipt

    @BeforeEach
    fun setUp() {
        val customerMapper = CustomerMapper(customerRepository)

        if (!customerRepository.findByName(daniel.name).isPresent) {
            customerRepository.save(customerMapper.toJpa(daniel))
        }

        receiptRepository.deleteAll()

        statement = Statement(daniel, dollars, customerRepository, ReceiptMapper(customerMapper), receiptRepository)

        danielsAccount =  SavingsAccount(daniel, dollars, statement)

        bankAccountRepository.save(savingsAccountMapper.toJpa(danielsAccount))

        dollars10DepositReceipt = credit(danielsAccount, Action.Deposit, dollars10)
        dollars10WithdrawReceipt = debit(danielsAccount, Action.Withdrawal, dollars10)
        minusDollars20Record = debit(danielsAccount, Action.Withdrawal, dollars20)
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

        assertFalse(
            statement.containsInOrder(
                dollars10WithdrawReceipt,
                dollars10DepositReceipt
            )
        )
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
}