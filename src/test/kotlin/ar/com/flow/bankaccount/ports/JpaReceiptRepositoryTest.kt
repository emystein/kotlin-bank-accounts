package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.jpa.JpaReceipt
import ar.com.flow.bankaccount.adapters.jpa.ReceiptRepository
import ar.com.flow.bankaccount.application.spring.BankAccountConfiguration
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.domain.TestObjects.francisco
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars100
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountConfiguration::class])
class JpaReceiptRepositoryTest {
    @Autowired
    private lateinit var statement: ReceiptRepository

    private lateinit var franciscosAccount: BankAccount
    private lateinit var dollars10DepositReceipt: Receipt

    @BeforeEach
    fun setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100)
        dollars10DepositReceipt = credit(franciscosAccount, Action.Deposit, dollars10)
    }

    @Test
    internal fun addReceiptToStatement() {
        val jpaReceipt = JpaReceipt.from(dollars10DepositReceipt)

        val createdJpaReceipt = statement.save(jpaReceipt)

        assertThat(statement.findById(createdJpaReceipt.id)).isNotEmpty
    }
}