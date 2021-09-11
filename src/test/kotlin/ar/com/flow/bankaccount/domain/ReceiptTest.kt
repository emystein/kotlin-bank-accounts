package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryAccountRegistry
import ar.com.flow.bankaccount.domain.Balance.Companion.positive
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars110
import ar.com.flow.money.TestMoney.dollars90
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReceiptTest {
    private val accountRegistry = InMemoryAccountRegistry()

    private lateinit var danielsAccount: BankAccount
    
    @BeforeEach
    fun setUp() {
        danielsAccount = accountRegistry.createSavingsAccountFor(daniel, dollars100)
    }

    @Test
    fun recordCreditResultBalance() {
        val receipt = credit(danielsAccount, Action.Deposit, dollars10)

        ReceiptAssert.assertThat(receipt)
            .hasResultBalance(positive(dollars110))
    }

    @Test
    fun recordDebitResultBalance() {
        val receipt = debit(danielsAccount, Action.Deposit, dollars10)

        ReceiptAssert.assertThat(receipt)
            .hasResultBalance(positive(dollars90))
    }
}
